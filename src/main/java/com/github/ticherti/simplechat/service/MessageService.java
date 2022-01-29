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
import com.github.ticherti.simplechat.service.parser.MessageParser;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import com.github.ticherti.simplechat.util.SearchVideoYoutube;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private MessageParser messageParser;


    @Transactional
    public ResponseMessageDTO save(SaveRequestMessageDTO requestMessageTo, User currentUser, long roomId) {
        log.info("Saving message");
        Room room = roomRepository.getById(roomId);
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

    public ResponseMessageDTO processMessage(SaveRequestMessageDTO messageDTO, User user, long roomId) {
        if (roomId == BOT_ROOM_ID) {
            chatBot(messageDTO);
            return messageMapper.saveToResponse(messageDTO);
        } else {
            return save(messageDTO, user, roomId);
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
        chatMessage.setContent(messageParser.botAction(text));
    }

}
