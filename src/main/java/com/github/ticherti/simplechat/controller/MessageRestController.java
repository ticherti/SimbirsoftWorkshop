package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.exception.NullMessageException;
import com.github.ticherti.simplechat.service.MessageService;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "rest/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageService messageService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody SaveRequestMessageDTO messageTo) {
        log.info("creating a message");
//        todo Extend entities from base abstract. Refactor messages classes, extract null checks.
        if (messageTo == null) {
            throw new NullMessageException();
        }
        return new ResponseEntity(messageService.save(messageTo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseMessageDTO read(@NotNull @PathVariable long id) {
        log.info("Getting a message " + id);
        return messageService.read(id);
    }

    @GetMapping("")
    public List<ResponseMessageDTO> readAll() {
        log.info("Getting all messages");
        return messageService.readAll();
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ResponseMessageDTO messageTo) {
        log.info("Updating a message " + messageTo.getId());
//        todo Find out if I need to get id in the parameters for consistency  and security check
        if (messageTo == null) {
            throw new NullMessageException();
        }
        messageService.update(messageTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NotNull @PathVariable long id) {
        log.info("deleting message " + id);
        messageService.delete(id);
    }
}
