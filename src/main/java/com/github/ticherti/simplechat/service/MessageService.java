package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.repository.MessageRepository;

import java.util.List;

public class MessageService {
    private MessageRepository messageRepository;

    public Message create(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> readAll() {
        return messageRepository.findAll();
    }

    public Message read(long id) {
        return messageRepository.getById(id);
    }

    public Message update(Message message) {
        return messageRepository.save(message);
    }

    //    probably should return something 
    public void delete(long id) {
        messageRepository.deleteById(id);
    }
}
