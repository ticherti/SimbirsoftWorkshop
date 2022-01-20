package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.security.AuthUser;
import com.github.ticherti.simplechat.service.MessageService;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "rest/rooms/{roomId}/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageRestController {
    //todo check room id usage in methods
    private MessageService messageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("principal.enabled==true")
    public ResponseEntity<?> create(@Valid @RequestBody SaveRequestMessageDTO messageTo,
                                    @AuthenticationPrincipal AuthUser currentUser) {
        log.info("creating a message");
        return new ResponseEntity(messageService.processMessage(messageTo, currentUser.getUser()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseMessageDTO read(@NotNull @PathVariable long id, @NotNull @PathVariable long roomId,
                                   @AuthenticationPrincipal AuthUser user) {
        log.info("Getting a message " + id);
        return messageService.read(id, roomId, user.getUser());
    }

    @GetMapping
    public List<ResponseMessageDTO> readAll(@NotNull @PathVariable long roomId,
                                            @AuthenticationPrincipal AuthUser currentUser) {
        log.info("Getting all messages. Auth user is {}", currentUser.getUser().isActive());
        return messageService.readAll(roomId, currentUser.getUser());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    todo Need to add newly added id in service check's. Look for bunnies.
    public void update(@Valid @RequestBody ResponseMessageDTO messageTo) {
        log.info("Updating a message " + messageTo.getId());
        messageService.update(messageTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("principal.enabled==true")
    public void delete(@NotNull @PathVariable long id) {
        log.info("deleting message " + id);
        messageService.delete(id);
    }
}
