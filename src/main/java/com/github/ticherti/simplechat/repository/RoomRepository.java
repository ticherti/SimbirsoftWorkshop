package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Room r WHERE r.id =:id")
//    int delete(long id);
}
