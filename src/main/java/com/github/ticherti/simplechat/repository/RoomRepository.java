package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
