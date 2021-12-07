package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.repository.RoomRepository;

import java.util.List;

public class RoomService {
    private RoomRepository roomRepository;

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> readAll() {
        return roomRepository.findAll();
    }

    public Room read(long id) {
        return roomRepository.getById(id);
    }

    public Room update(Room room) {
        return roomRepository.save(room);
    }

    //    probably should return something
    public void delete(long id) {
        roomRepository.deleteById(id);
    }
}
