package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.service.MessageService;
import com.github.ticherti.simplechat.to.ResponseMessageTo;
import com.github.ticherti.simplechat.to.SaveRequestMessageTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.ticherti.simplechat.mapper.MessageMapper.messageMapper;

@RestController
@RequestMapping(value = "rest/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessageTo create(@RequestBody SaveRequestMessageTo messageTo) {
        Message message = messageService.save(messageMapper.toEntity(messageTo));
//        todo Add not null check, probably in services
        return messageMapper.toTO(message);
    }

    @GetMapping(value = "/{id}")
    public ResponseMessageTo read(@PathVariable long id) {
//        todo Check consistency somehow
        return messageMapper.toTO(messageService.read(id));
    }
    @GetMapping
    public List<ResponseMessageTo> readAll(){
//        todo Check consistency somehow
        return messageMapper.allToTOs(messageService.readAll());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody ResponseMessageTo messageTo){
//        todo Check consistency and probably not found case
//        Find out if I need to get id in the parameters
            Message message = messageService.update(messageMapper.toEntity(messageTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        messageService.delete(id);
    }
}
