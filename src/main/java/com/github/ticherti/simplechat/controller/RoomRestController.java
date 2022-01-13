package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.security.AuthUser;
import com.github.ticherti.simplechat.service.RoomService;
import com.github.ticherti.simplechat.to.ResponseRoomDTO;
import com.github.ticherti.simplechat.to.SaveRequestRoomDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "rest/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomRestController {
    private static final Logger log = getLogger(RoomRestController.class);
    @Autowired
    private RoomService roomService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseRoomDTO create(@Valid @RequestBody SaveRequestRoomDTO roomTo, @AuthenticationPrincipal AuthUser user) {
        log.info("creating a room");
        return roomService.save(roomTo, user.getUser());
    }
//todo some thin point here. Not sure how to implement it now
    @GetMapping("/{id}")
    public ResponseRoomDTO read(@NotNull @PathVariable long id) {
        log.info("Getting a room " + id);
        return roomService.read(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users.write')")
    public List<ResponseRoomDTO> readAll() {
        log.info("Getting all rooms");
        return roomService.readAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rename(@Valid @RequestBody ResponseRoomDTO roomTo, @AuthenticationPrincipal AuthUser user) {
        log.info("Updating a room " + roomTo.getId());
        roomService.rename(roomTo, user.getUser());
    }

    @PutMapping("/enter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addUser(@RequestParam long roomId, @RequestParam long userId,
                        @AuthenticationPrincipal AuthUser user) {
        log.info("Inviting into a room " + roomId);
        roomService.addUser(roomId, userId, user.getUser());
    }
    @PutMapping("/exit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@RequestParam long roomId, @RequestParam long userId,
                        @AuthenticationPrincipal AuthUser user) {
        log.info("Inviting into a room " + roomId);
        roomService.removeUser(roomId, userId, user.getUser());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NotNull @PathVariable long id) {
        log.info("deleting room " + id);
        roomService.delete(id);
    }
}
