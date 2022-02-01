package com.github.ticherti.simplechat.service.parser;

import com.github.ticherti.simplechat.entity.Permission;
import com.github.ticherti.simplechat.entity.Role;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.entity.Videos;
import com.github.ticherti.simplechat.exception.NotPermittedException;
import com.github.ticherti.simplechat.exception.ParseException;
import com.github.ticherti.simplechat.security.AuthUser;
import com.github.ticherti.simplechat.service.RoomService;
import com.github.ticherti.simplechat.service.UserService;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestRoomDTO;
import com.github.ticherti.simplechat.util.SearchVideoYoutube;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Component
@AllArgsConstructor
public class MessageParser {

    private static final String SPLIT_PATTERN = "\\s+";
    private static final String HELP = """
            Команды
            Комнаты:
            1 . //room create {Название комнаты} - создает комнаты;
            -c закрытая комната. Только (владелец, модератор и админ) может
            добавлять/удалять пользователей из комнаты.
            2. //room remove {Название комнаты} - удаляет комнату (владелец и админ);
            3. //room rename {Название комнаты} - переименование комнаты (владелец и
            админ);
            4. //room connect {Название комнаты} - войти в комнату;
            -l {login пользователя} - добавить пользователя в комнату
            5. //room disconnect - выйти из текущей комнаты;
            Пользователи:
            1 . //user rename {login пользователя} (владелец и админ);
            2. //user ban;
            -l {login пользователя} - выгоняет пользователя из всех комнат
            -m {Количество минут} - время на которое пользователь не сможет войти.
            3. //user moderator {login пользователя} - действия над модераторами.
            -n - назначить пользователя модератором.
            -d - “разжаловать” пользователя.
            Боты:
            1 . //yBot find -k -l {название канала}||{название видео} - в ответ бот присылает
            ссылку на ролик;
            -v - выводит количество текущих просмотров.
            -l - выводит количество лайков под видео.
            2. //yBot videoCommentRandom {имя канала}||{Название ролика} 
            - Среди комментариев к ролику рандомно выбирается 1
            3. //yBot channelInfo {имя канала}
            - Бот выводит имя канала и ссылки на последние 5 роликов
            2. //yBot help - список доступных команд для взаимодействия.
            Другие:
            1 . //help - выводит список доступных команд.""";

    private SearchVideoYoutube searchVideoYoutube;
    private UserService userService;
    private RoomService roomService;

    public void parseMessage(SaveRequestMessageDTO chatMessage) {
        String text = chatMessage.getContent();

        String[] words = text.trim().split(SPLIT_PATTERN);

        if (text.startsWith("//room")) {
            chatMessage.setContent(roomAction(words));
        } else if (text.startsWith("//user")) {
            chatMessage.setContent(userAction(words));
        } else if (text.startsWith("//yBot")) {
            chatMessage.setContent(youtubeAction(text));
        } else if (text.startsWith("//help") || text.startsWith("//yBot help")) {
            chatMessage.setContent(HELP);
        } else {
            log.info("Wrong bot command {}", text);
            chatMessage.setContent("Wrong bot command - " + text);
        }
    }

    private String roomAction(String[] words) {
        if (words.length < 3) {
            throw new ParseException();
        }
        switch (words[1]) {
            case "create" -> {
                return parseRoomCreate(words);
            }
            case "remove" -> {
                return parseRoomRemove(words);
            }
            case "rename" -> {
                return parseRoomRename(words);
            }
            case "connect" -> {
                return parseRoomConnect(words);
            }
            case "disconnect" -> {
                return parseRoomDisconnect(words);
            }
            default -> {
                throw new ParseException();
            }
        }
    }

    private String parseRoomCreate(String[] words) {
        boolean isPrivate = (words.length >= 4 && words[3].equals("-c")) ? true : false;
        roomService.save(new SaveRequestRoomDTO(words[2], isPrivate), getAuthUser().getUser());
        return "Room is successfully created.";
    }

    private String parseRoomRemove(String[] words) {
        roomService.deleteByName(words[2], getAuthUser().getUser());
        return "Room is successfully deleted.";
    }

    private String parseRoomRename(String[] words) {
        checkLength(words.length, 4);
        roomService.renameByName(words[2], words[3], getAuthUser().getUser());
        return "Room is successfully renamed.";
    }

    private String parseRoomConnect(String[] words) {
        User user = getAuthUser().getUser();
        String login = (words.length >= 5 && words[3].equals("-l")) ? words[4] : user.getLogin();
        roomService.addUser(words[2], login, user);
        return "User entered";
    }

    private String parseRoomDisconnect(String[] words) {
        User user = getAuthUser().getUser();
        String login = (words.length >= 5 && words[3].equals("-l")) ? words[4] : user.getLogin();
        roomService.removeUser(words[2], login, user);
        return "User left";
    }

    private String userAction(String[] words) {
        if (words.length < 3) {
            throw new ParseException();
        }
        switch (words[1]) {
            case "rename" -> {
                return parseUserRename(words);
            }
            case "ban" -> {
                return parseUserBan(words);
            }
            case "moderator" -> {
                return parseUserModerator(words);
            }
            default -> {
                throw new ParseException();
            }
        }
    }

    private String youtubeAction(String text) {
        if (text.contains("//yBot find")) {
            return parseYoutubeFind(text);
        }
        if (text.contains("//yBot videoCommentRandom")) {
            return parseYoutubeVideoComment(text);
        }
        if (text.contains("//yBot channelInfo")) {
            return parseChannelInfo(text);
        } else {
            throw new ParseException();
        }
    }

    private String parseYoutubeFind(String text) {
        final String SPLIT_PATTERN_FIND = "//yBot find|(\\|\\|)|-l|-v";
        int likes, view;
        String movieName;
        String channelName;
        String channelId;
        StringBuffer message;
        List<Videos> videoList = new ArrayList<>();

        String[] splitCommands = text.split(SPLIT_PATTERN_FIND);

        likes = text.lastIndexOf("-l");
        view = text.lastIndexOf("-v");

        if (likes != -1 & view != -1) {
            if (splitCommands.length < 4) {
                movieName = splitCommands[1].trim();
                channelName = "";
            } else {
                movieName = splitCommands[2].trim();
                channelName = splitCommands[1].trim();
            }
        } else {
            if (splitCommands.length < 3) {
                movieName = splitCommands[1].trim();
                channelName = "";
            } else {
                movieName = splitCommands[2].trim();
                channelName = splitCommands[1].trim();
            }
        }
        message = new StringBuffer();
        try {
            if (!channelName.isEmpty()) {
                channelId = searchVideoYoutube.getChannelId(channelName);
            } else {
                channelId = "";
            }

            videoList = searchVideoYoutube.getVideoList(movieName, channelId, Long.valueOf(1), false);

            if (videoList.size() == 0) {
                return message.append(text).append("  not found. Use the command '//help'").toString();
            }

            for (Videos videos : videoList) {
                message.append("https://www.youtube.com/watch?v=" + videos.getId() + "  " + videos.getTitle() + "  ");
                if (likes != -1 | view != -1) {
                    message.append(view != -1 ? (videos.getViewCount() + " views") : "");
                    message.append(likes != -1 & view != -1 ? " | " : "");

                    message.append(likes != -1 ? (videos.getLikeCount() + " likes") : "");
                }
            }
        } catch (GoogleJsonResponseException ex) {
            log.error("GoogleJsonResponseException code: " + ex.getDetails().getCode() + " : "
                    + ex.getDetails().getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        } catch (IOException ex) {
            log.error("IOException: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        } catch (Throwable ex) {
            log.error("Throwable: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        }
        videoList.clear();
        return message.toString();
    }

    private String parseYoutubeVideoComment(String text) {
        final String SPLIT_PATTERN = "//yBot videoCommentRandom|(\\|\\|)";
        String movieName;
        String channelName;
        String channelId;
        StringBuffer message;
        List<Videos> videoList = new ArrayList<>();

        String[] splitCommands = text.split(SPLIT_PATTERN);

        movieName = splitCommands[2].trim();
        channelName = splitCommands[1].trim();

        message = new StringBuffer();
        try {
            if (!channelName.isEmpty()) {
                channelId = searchVideoYoutube.getChannelId(channelName);
            } else {
                channelId = "";
            }

            videoList = searchVideoYoutube.getVideoList(movieName, channelId, Long.valueOf(1), false);

            if (videoList.size() == 0) {
                return message.append(text).append("  not found. Use the command '//help'").toString();
            }

            for (Videos videos : videoList) {
                Map<String, String> comments = searchVideoYoutube.getNameAndCommentMap(videos);
                for (Map.Entry<String, String> entry : comments.entrySet()) {
                    message.append(entry.getKey() + " : " + entry.getValue() + "\n");
                }
            }
        } catch (GoogleJsonResponseException ex) {
            log.error("GoogleJsonResponseException code: " + ex.getDetails().getCode() + " : "
                    + ex.getDetails().getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        } catch (IOException ex) {
            log.error("IOException: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        } catch (Throwable ex) {
            log.error("Throwable: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        }
        videoList.clear();
        return message.toString();
    }

    private String parseChannelInfo(String text) {
        final String SPLIT_PATTERN = "//yBot channelInfo()";
        String channelName;
        String channelId;
        StringBuffer message;
        List<Videos> videoList = new ArrayList<>();

        String[] splitCommands = text.split(SPLIT_PATTERN);
        channelName = splitCommands[1].trim();

        message = new StringBuffer();
        try {
            if (!channelName.isEmpty()) {
                channelId = searchVideoYoutube.getChannelId(channelName);
            } else {
                channelId = "";
            }

            videoList = searchVideoYoutube.getVideoList(channelId, 5L, false);

            if (videoList.size() == 0) {
                return message.append(text).append("  not found. Use the command '//help'").toString();
            }
            final String youtubeAddress = "https://www.youtube.com/watch?v=";
            final String delimeter = " | ";
            message.append(channelName).append(delimeter);
            for (Videos videos : videoList) {
                message.append(youtubeAddress).append(videos.getId()).append(delimeter);
            }
            int lastDelimeter = message.lastIndexOf(delimeter);
            message.delete(lastDelimeter, lastDelimeter + 2);
        } catch (GoogleJsonResponseException ex) {
            log.error("GoogleJsonResponseException code: " + ex.getDetails().getCode() + " : "
                    + ex.getDetails().getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        } catch (IOException ex) {
            log.error("IOException: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        } catch (Throwable ex) {
            log.error("Throwable: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - ").append(text);
        }
        videoList.clear();
        return message.toString();
    }

    private String parseUserRename(String[] words) {
        checkLength(words.length, 4);
        AuthUser currentUser = getAuthUser();
        String name = words[2];
        if (!name.equals(currentUser.getUsername()) &&
                !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Permission.RENAME_USER.getPermission()))) {
            throw new NotPermittedException("Not authorized to rename this user");
        }
        String newName = words[3];
        userService.rename(name, newName);
        return "User renamed " + newName;
    }

    @Secured({"ADMINISTRATOR", "MODERATOR"})
    private String parseUserBan(String[] words) {
        Integer minutes = null;
        OptionalInt indexOpt = IntStream.range(0, words.length)
                .filter(i -> "-m".equals(words[i]))
                .findFirst();
        if (indexOpt.isPresent()) {
            try {
                minutes = Integer.parseInt(words[indexOpt.getAsInt() + 1]);
            } catch (NumberFormatException e) {
                throw new ParseException();
            }
        }
        if (Arrays.stream(words).anyMatch(s -> s.equals("-l"))) {
            roomService.removeUserFromAll(words[2], getAuthUser().getUser());
        }

        userService.banByLogin(words[2], minutes);
        return "User's banned";
    }

    @Secured({"ADMINISTRATOR"})
    private String parseUserModerator(String[] words) {
        checkLength(words.length, 4);
        String login = words[2];
        switch (words[3]) {
            case "-n" -> {
                userService.setModerator(login, Role.MODERATOR.name());
                return "Moderator set";
            }
            case "-d" -> {
                userService.setModerator(login, Role.USER.name());
                return "Moderator demote";
            }
            default -> {
                throw new ParseException();
            }
        }
    }

    private AuthUser getAuthUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void checkLength(int length, int needed) {
        if (length < needed) {
            throw new ParseException();
        }
    }
}
