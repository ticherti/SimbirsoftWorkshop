package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.NotPermittedException;
import com.github.ticherti.simplechat.exception.RoomNotFoundException;
import com.github.ticherti.simplechat.exception.UserNotFoundException;
import com.github.ticherti.simplechat.mapper.RoomMapper;
import com.github.ticherti.simplechat.repository.RoomRepository;
import com.github.ticherti.simplechat.repository.UserRepository;
import com.github.ticherti.simplechat.to.ResponseRoomDTO;
import com.github.ticherti.simplechat.to.SaveRequestRoomDTO;
import com.github.ticherti.simplechat.util.UserUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.github.ticherti.simplechat.util.UserUtil.checkCreatorAndPermission;
import static com.github.ticherti.simplechat.util.UserUtil.ckeckBan;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class RoomService {
    private static final Logger log = getLogger(RoomService.class);

    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private RoomMapper roomMapper;

    @Transactional
    public ResponseRoomDTO save(SaveRequestRoomDTO requestRoomTo, User user) {
        log.info("Saving room");
        ckeckBan(user);
        Room room = roomMapper.toEntity(requestRoomTo);
        room.setCreator(user);
        room.setUsers(Collections.singletonList(user));
        return roomMapper.toTO(roomRepository.save(room));
    }

    @Transactional(readOnly = true)
    public List<ResponseRoomDTO> readAll() {
        return roomMapper.allToTOs(roomRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseRoomDTO read(long id) {
        log.info("Reading room");
        return roomMapper.toTO(roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id)));
    }

    //todo id null and efficacy problem here
    @Transactional
    public ResponseRoomDTO rename(ResponseRoomDTO responseRoomTo, User user) {
        long id = responseRoomTo.getId();
        log.info("Updating room with id {}", id);
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
        checkCreatorAndPermission(user, room);
        room.setName(responseRoomTo.getName());
        return roomMapper.toTO(room);
    }

    @Transactional
    public void addUser(long roomId, long userId, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        List<User> users = room.getUsers();
        ckeckBan(user);
        if (room.isPrivate() && users.size() >= 2) {
            checkCreatorAndPermission(user, room);
            throw new NotPermittedException("The room is not permitted to have more than two persons");
        }
        User addedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
       users.add(addedUser);
       room.setUsers(users);
    }

    @Transactional
    public void removeUser(long roomId, long userId, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        checkCreatorAndPermission(user, room);
        List<User> users = room.getUsers();
        User removedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (!users.contains(user)) {
            throw new NotPermittedException("No such user in the room");
        }
        users.remove(removedUser);
    }

    @Transactional
    public void delete(long id, User user) {
        log.info("Deleting room");
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
        checkCreatorAndPermission(user, room);
        roomRepository.deleteById(id);
    }
}
