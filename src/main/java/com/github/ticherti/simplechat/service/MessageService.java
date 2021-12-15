package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.exception.MessageNotFoundException;
import com.github.ticherti.simplechat.mapper.MessageMapper;
import com.github.ticherti.simplechat.repository.MessageRepository;
import com.github.ticherti.simplechat.to.ResponseMessageTo;
import com.github.ticherti.simplechat.to.SaveRequestMessageTo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
public class MessageService {
    private static final Logger log = getLogger(MessageService.class);
    @Autowired
    private MessageRepository messageRepository;
    private MessageMapper messageMapper;

    @Transactional
    public ResponseMessageTo save(SaveRequestMessageTo requestMessageTo) {
        log.info("Saving message");
        return messageMapper.toTO(messageRepository.save(messageMapper.toEntity(requestMessageTo)));
    }

    @Transactional(readOnly = true)
    public List<ResponseMessageTo> readAll() {
        return messageMapper.allToTOs(messageRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseMessageTo read(long id) {
//        There is no privacy check here
        log.info("Reading message");
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return messageMapper.toTO(message.get());
        } else {
            throw new MessageNotFoundException(id);
        }
    }

    @Transactional
    public ResponseMessageTo update(ResponseMessageTo responseMessageTo) {
        Optional<Message> existedMessage = messageRepository.findById(responseMessageTo.getId());
        long id = responseMessageTo.getId();
        if (existedMessage.isPresent()) {
            Message message = messageMapper.toEntity(responseMessageTo);
            message.setId(id);
            return messageMapper.toTO(message);
        } else {
            throw new MessageNotFoundException(id);
        }
    }

    public void delete(long id) {
        log.info("Deleting message");
        if (messageRepository.delete(id) == 0) {
            throw new MessageNotFoundException(id);
        }
    }
}
