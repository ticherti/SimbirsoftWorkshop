package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.id=:id")
    void delete(long id);
}
