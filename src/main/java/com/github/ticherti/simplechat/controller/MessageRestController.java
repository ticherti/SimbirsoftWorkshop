package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.service.MessageService;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.ticherti.simplechat.mapper.MessageMapper.messageMapper;

@RestController
@RequestMapping(value = "rest/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageService messageService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessageDTO create(@RequestBody SaveRequestMessageDTO messageTo) {
        log.info("creating a message");
        Message message = messageService.save(messageMapper.toEntity(messageTo));
//        todo Add not null check, probably in services
        return messageMapper.toTO(message);
    }

    @GetMapping("/{id}")
    public ResponseMessageDTO read(@PathVariable long id) {
        log.info("Getting a message " + id);
//        todo Check consistency somehow
        return messageMapper.toTO(messageService.read(id));
    }

    @GetMapping("")
    public List<ResponseMessageDTO> readAll() {
        log.info("Getting all messages");
//        todo Check consistency somehow
        return messageMapper.allToTOs(messageService.readAll());
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody ResponseMessageDTO messageTo) {
        log.info("updating message " + messageTo.getId());
//        todo Check consistency and probably not found case
//        Find out if I need to get id in the parameters for consistency check
        Message message = messageService.update(messageMapper.toEntity(messageTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("deleting message " + id);
        messageService.delete(id);
    }
}
