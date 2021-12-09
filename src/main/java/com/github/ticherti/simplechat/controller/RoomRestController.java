package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.service.RoomService;
import com.github.ticherti.simplechat.to.ResponseRoomTo;
import com.github.ticherti.simplechat.to.SaveRequestRoomTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.ticherti.simplechat.mapper.RoomMapper.roomMapper;

@RestController
@RequestMapping(value = "rest/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseRoomTo create(@RequestBody SaveRequestRoomTo roomTo) {
        Room room = roomService.save(roomMapper.toEntity(roomTo));
//        todo Add not null check, probably in services
        return roomMapper.toTO(room);
    }

    @GetMapping( "/{id}")
    public ResponseRoomTo read(@PathVariable long id) {
//        todo Check consistency somehow
        return roomMapper.toTO(roomService.read(id));
    }
    @GetMapping("")
    public List<ResponseRoomTo> readAll(){
//        todo Check consistency somehow
        return roomMapper.allToTOs(roomService.readAll());
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody ResponseRoomTo roomTo){
//        todo Check consistency and probably not found case
//        Find out if I need to get id in the parameters
            Room room = roomService.update(roomMapper.toEntity(roomTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        roomService.delete(id);
    }
}
