package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.exception.RoomNotFoundException;
import com.github.ticherti.simplechat.mapper.RoomMapper;
import com.github.ticherti.simplechat.repository.RoomRepository;
import com.github.ticherti.simplechat.to.ResponseRoomTo;
import com.github.ticherti.simplechat.to.SaveRequestRoomTo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
public class RoomService {
    private static final Logger log = getLogger(RoomService.class);

    private RoomRepository roomRepository;
    private RoomMapper roomMapper;

    @Transactional
    public ResponseRoomTo save(SaveRequestRoomTo requestRoomTo) {
        log.info("Saving room");
        return roomMapper.toTO(roomRepository.save(roomMapper.toEntity(requestRoomTo)));
    }

    @Transactional(readOnly = true)
    public List<ResponseRoomTo> readAll() {
        return roomMapper.allToTOs(roomRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseRoomTo read(long id) {
//        There is no privacy check here
        log.info("Reading room");
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            return roomMapper.toTO(room.get());
        } else {
            throw new RoomNotFoundException(id);
        }
    }

    @Transactional
    public ResponseRoomTo update(ResponseRoomTo responseRoomTo) {
        Optional<Room> existedRoom = roomRepository.findById(responseRoomTo.getId());
        long id = responseRoomTo.getId();
        if (existedRoom.isPresent()) {
            Room room = roomMapper.toEntity(responseRoomTo);
            room.setId(id);
            return roomMapper.toTO(room);
        } else {
            throw new RoomNotFoundException(id);
        }
    }

    public void delete(long id) {
        log.info("Deleting room");
        if (roomRepository.delete(id) == 0) {
            throw new RoomNotFoundException(id);
        }
    }
}
