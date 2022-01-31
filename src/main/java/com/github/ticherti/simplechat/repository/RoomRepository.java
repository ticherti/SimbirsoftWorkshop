package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT * FROM rooms_users WHERE user_id=:userId AND room_id=:roomId", nativeQuery=true)
    Integer checkUserInRoom(long userId, long roomId);

    Optional<Room> findByName(String name);
}
