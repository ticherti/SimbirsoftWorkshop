package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.RoomNotFoundException;
import com.github.ticherti.simplechat.mapper.RoomMapper;
import com.github.ticherti.simplechat.repository.RoomRepository;
import com.github.ticherti.simplechat.repository.UserRepository;
import com.github.ticherti.simplechat.to.ResponseRoomDTO;
import com.github.ticherti.simplechat.to.SaveRequestRoomDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class RoomService {
    private static final Logger log = getLogger(RoomService.class);

    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private RoomMapper roomMapper;

    @Transactional
    public ResponseRoomDTO save(SaveRequestRoomDTO requestRoomTo) {
//        todo check user, throw some exceptions
        log.info("Saving room");
        User creator = userRepository.getById(requestRoomTo.getUserId());
        Room room = roomMapper.toEntity(requestRoomTo);
        room.setCreator(creator);
        return roomMapper.toTO(roomRepository.save(room));
    }

    @Transactional(readOnly = true)
    public List<ResponseRoomDTO> readAll() {
        return roomMapper.allToTOs(roomRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseRoomDTO read(long id) {
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
    public ResponseRoomDTO update(ResponseRoomDTO responseRoomTo) {
//        todo something with id here. Also authority question and private-don't-change one.
        Optional<Room> existedRoom = roomRepository.findById(responseRoomTo.getId());
        long id = responseRoomTo.getId();
        if (existedRoom.isPresent()) {
            //        todo check user, throw some exceptions
            log.info("Updating room, room isPresent");
            Room room = roomMapper.toEntity(responseRoomTo);
            User creator = userRepository.getById(responseRoomTo.getUserId());
            room.setCreator(creator);
            room.setId(id);
            return roomMapper.toTO(roomRepository.save(room));
        } else {
            throw new RoomNotFoundException(id);
        }
    }

    public void delete(long id) {
        log.info("Deleting room");
        Optional<Room> room = roomRepository.findById(id);
        if (!room.isPresent()) {
            throw new RoomNotFoundException(id);
        }
        roomRepository.deleteById(id);
    }
}
