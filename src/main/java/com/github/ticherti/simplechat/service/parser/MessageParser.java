package com.github.ticherti.simplechat.service.parser;

import com.github.ticherti.simplechat.entity.Videos;
import com.github.ticherti.simplechat.util.SearchVideoYoutube;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class MessageParser {

    private SearchVideoYoutube searchVideoYoutube;

    public String botAction(String text) {
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
//        if (message.length() > 0) {
//            return message.toString();
//        }

        return message.toString();
    }
}
