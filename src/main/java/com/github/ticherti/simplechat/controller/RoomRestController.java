package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.exception.NullRoomException;
import com.github.ticherti.simplechat.service.RoomService;
import com.github.ticherti.simplechat.to.ResponseRoomDTO;
import com.github.ticherti.simplechat.to.SaveRequestRoomDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseRoomDTO create(@Valid @RequestBody SaveRequestRoomDTO roomTo) {
        log.info("creating a room");
//        todo Extend entities from base abstract. Refactor rooms classes, extract null checks.
        if (roomTo == null) {
            throw new NullRoomException();
        }
        return roomService.save(roomTo);
    }

    @GetMapping("/{id}")
    public ResponseRoomDTO read(@NotNull @PathVariable long id) {
        log.info("Getting a room " + id);
        return roomService.read(id);
    }

    @GetMapping("")
    public List<ResponseRoomDTO> readAll() {
        log.info("Getting all rooms");
        return roomService.readAll();
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ResponseRoomDTO roomTo) {
        log.info("Updating a room " + roomTo.getId());
//        todo Find out if I need to get id in the parameters for consistency  and security check
        if (roomTo == null) {
            throw new NullRoomException();
        }
        roomService.update(roomTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    todo Check this out when I have more info about how to get users to manage their room
//    @PreAuthorize("hasAuthority('users.write')")
    public void delete(@NotNull @PathVariable long id) {
        log.info("deleting room " + id);
        roomService.delete(id);
    }
}
