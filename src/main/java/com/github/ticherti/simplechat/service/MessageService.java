package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.MessageNotFoundException;
import com.github.ticherti.simplechat.exception.RoomNotFoundException;
import com.github.ticherti.simplechat.mapper.MessageMapper;
import com.github.ticherti.simplechat.repository.MessageRepository;
import com.github.ticherti.simplechat.repository.RoomRepository;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
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

    private MessageRepository messageRepository;
    private RoomRepository roomRepository;
    private MessageMapper messageMapper;

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

    public void delete(long id) {
        log.info("Deleting message");
        if (messageRepository.delete(id) == 0) {
            throw new MessageNotFoundException(id);
        }
    }
}
