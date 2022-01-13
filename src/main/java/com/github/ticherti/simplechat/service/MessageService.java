package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.MessageNotFoundException;
import com.github.ticherti.simplechat.mapper.MessageMapper;
import com.github.ticherti.simplechat.repository.MessageRepository;
import com.github.ticherti.simplechat.repository.RoomRepository;
import com.github.ticherti.simplechat.repository.UserRepository;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import com.github.ticherti.simplechat.util.UserUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class MessageService {
    private static final Logger log = getLogger(MessageService.class);

    private MessageRepository messageRepository;
    private RoomRepository roomRepository;
    private MessageMapper messageMapper;

    @Transactional
    public ResponseMessageDTO save(SaveRequestMessageDTO requestMessageTo, User user) {
        log.info("Saving message");
        UserUtil.ckeckBan(user);
        Room room = roomRepository.getById(requestMessageTo.getRoomId());
        Message message = messageMapper.toEntity(requestMessageTo);
        message.setRoom(room);
        message.setUser(user);
        return messageMapper.toTO(messageRepository.save(message));
    }

    @Transactional(readOnly = true)
    public List<ResponseMessageDTO> readAll() {
        return messageMapper.allToTOs(messageRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseMessageDTO read(long id) {
//        There is no privacy check here
        log.info("Reading message");
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
