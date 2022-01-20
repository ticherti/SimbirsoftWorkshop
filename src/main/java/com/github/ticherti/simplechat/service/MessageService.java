package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.entity.Videos;
import com.github.ticherti.simplechat.exception.MessageNotFoundException;
import com.github.ticherti.simplechat.exception.RoomNotFoundException;
import com.github.ticherti.simplechat.mapper.MessageMapper;
import com.github.ticherti.simplechat.repository.MessageRepository;
import com.github.ticherti.simplechat.repository.RoomRepository;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import com.github.ticherti.simplechat.util.SearchVideoYoutube;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.ticherti.simplechat.util.UserUtil.checkEnteredUser;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {
    private final static Long BOT_ROOM_ID = 0L;
    private MessageRepository messageRepository;
    private RoomRepository roomRepository;
    private MessageMapper messageMapper;

    private SearchVideoYoutube searchVideoYoutube;

    @Transactional
    public ResponseMessageDTO save(SaveRequestMessageDTO requestMessageTo, User currentUser) {
        log.info("Saving message");
        long roomId = requestMessageTo.getRoomId();
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        checkEnteredUser(roomRepository.checkUserInRoom(currentUser.getId(), roomId));
        Message message = messageMapper.toEntity(requestMessageTo);
        message.setRoom(room);
        message.setUser(currentUser);
        return messageMapper.toTO(messageRepository.save(message));
    }

    @Transactional(readOnly = true)
    public List<ResponseMessageDTO> readAll(long roomId, User currentUser) {
        checkEnteredUser(roomRepository.checkUserInRoom(currentUser.getId(), roomId));
        return messageMapper.allToTOs(messageRepository.findAllByRoom(roomId));
    }

    @Transactional(readOnly = true)
    public ResponseMessageDTO read(long id, long roomId, User currentUser) {
        log.info("Reading message");
        checkEnteredUser(roomRepository.checkUserInRoom(currentUser.getId(), roomId));
        return messageMapper.toTO(messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id)));
    }

    @Transactional
    public ResponseMessageDTO update(ResponseMessageDTO responseMessageTo) {
        long id = responseMessageTo.getId();
        messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));

        Message message = messageMapper.toEntity(responseMessageTo);
        message.setId(id);
        return messageMapper.toTO(messageRepository.save(message));
    }

    @Transactional
    public void delete(long id) {
        log.info("Deleting message");
        if (messageRepository.delete(id) == 0) {
            throw new MessageNotFoundException(id);
        }
    }

    public ResponseMessageDTO processMessage(SaveRequestMessageDTO messageDTO, User user) {
        if (messageDTO.getRoomId() == 0) {
            chatBot(messageDTO);
            return messageMapper.saveToResponse(messageDTO);
        } else {
            return save(messageDTO, user);
        }
    }

    //    todo check help commands here
    private void chatBot(SaveRequestMessageDTO chatMessage) {
        String text = chatMessage.getContent();
//        if (text.startsWith("//room")) {
//            botRoomAction(chatMessage);
//        } else if (text.startsWith("//user")) {
//            botUserAction(chatMessage);
//        } else if (text.startsWith("//yBot") || text.startsWith("//help")) {
//            botAction(chatMessage);
//        } else {
//            log.info("Wrong bot command {}", text);
//            chatMessage.setContent("Wrong bot command - " + text);
//        }
        botAction(chatMessage);
    }

    private void botAction(SaveRequestMessageDTO chatMessage) {
        final String SPLIT_PATTERN_FIND = "//yBot find|(\\|\\|)|-l|-v";
        int likes, view;
        String movieName;
        String channelName;
        String channelId;
        StringBuffer message;
        List<Videos> videoList = new ArrayList<>();
        String text = chatMessage.getContent();
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
                chatMessage.setContent("Not found movie - '" + text + "'. Use the command '//help'");
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
            message.append("Use the command '//help'. Error command to find movie - " + text);
        } catch (IOException ex) {
            log.error("IOException: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - " + text);
        } catch (Throwable ex) {
            log.error("Throwable: " + ex.getMessage());
            message.append("Use the command '//help'. Error command to find movie - " + text);
        }
        if (message.length() > 0) {
            chatMessage.setContent(message.toString());
        }
        videoList.clear();
    }
}
