package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Permission;
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
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.github.ticherti.simplechat.util.UserUtil.checkCreatorAndPermission;
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
        Room room = roomMapper.toEntity(requestRoomTo);
        room.setCreator(user);
        room.setUsers(Collections.singleton(user));
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

    @Transactional
    public ResponseRoomDTO renameById(ResponseRoomDTO responseRoomTo, User user) {
        long id = responseRoomTo.getId();
        return rename(find(id), responseRoomTo.getName(), user);
    }

    @Transactional
    public ResponseRoomDTO renameByName(String oldName, String newName, User user) {
        return rename(find(oldName), newName, user);
    }

    @Transactional
    public void addUser(long roomId, long userId, User user) {
        Room room = find(roomId);
        Set<User> users = room.getUsers();
        if (room.isPrivate() && users.size() >= 2) {
            throw new NotPermittedException("The room is not permitted to have more than two persons");
        }
        checkCreatorAndPermission(user, room, Permission.CONNECT_USER);
        User addedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        users.add(addedUser);
        room.setUsers(users);
    }

    @Transactional
    public void addUser(String roomName, String login, User user) {
        Room room = find(roomName);
        Set<User> users = room.getUsers();
        if (room.isPrivate() && users.size() >= 2) {
            throw new NotPermittedException("The room is not permitted to have more than two persons");
        }
        checkCreatorAndPermission(user, room, Permission.CONNECT_USER);
        User addedUser = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
        users.add(addedUser);
        room.setUsers(users);
    }

    @Transactional
    public void removeUser(long roomId, long userId, User user) {
        Room room = find(roomId);
        if (user.getId() != userId) {
            checkCreatorAndPermission(user, room, Permission.DISCONNECT_USER);
        }
        Set<User> users = room.getUsers();
        User removedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (!users.contains(user)) {
            throw new NotPermittedException("No such user in the room");
        }
        users.remove(removedUser);
    }

    @Transactional
    public void removeUser(String roomName, String login, User user) {
        Room room = find(roomName);
        if (user.getLogin() != login) {
            checkCreatorAndPermission(user, room, Permission.DISCONNECT_USER);
        }
        Set<User> users = room.getUsers();
        User removedUser = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
        if (!users.contains(user)) {
            throw new NotPermittedException("No such user in the room");
        }
        users.remove(removedUser);
    }

    @Transactional
    public void removeUserFromAll(String login, User user) {
        if (user.getRole().getPermissions().contains(Permission.DISCONNECT_USER)) {
            User removedUser = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
            removedUser.getRooms().forEach(r -> r.getUsers().remove(removedUser));
        }
    }

    @Transactional
    public void deleteById(long id, User user) {
        delete(id, find(id), user);
    }

    @Transactional
    public void deleteByName(String name, User user) {
        Room room = find(name);
        delete(room.getId(), room, user);
    }

    private ResponseRoomDTO rename(Room room, String newName, User user) {
        log.info("Updating room with id {}", room.getId());

        checkCreatorAndPermission(user, room, Permission.RENAME_ROOM);
        room.setName(newName);
        return roomMapper.toTO(room);
    }

    private void delete(long id, Room room, User user) {
        log.info("Deleting room");
        checkCreatorAndPermission(user, room, Permission.DELETE_ROOM);
        roomRepository.deleteById(id);
    }

    private Room find(long id) {
        return roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
    }

    private Room find(String name) {
        return roomRepository.findByName(name).orElseThrow(() -> new RoomNotFoundException(name));
    }
}
