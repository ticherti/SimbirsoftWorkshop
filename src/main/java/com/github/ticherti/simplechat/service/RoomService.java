package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room save(Room room) {
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
