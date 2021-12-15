package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Room m WHERE m.id=:id")
    int delete(long id);
}
