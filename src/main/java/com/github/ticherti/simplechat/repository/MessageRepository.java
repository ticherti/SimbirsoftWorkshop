package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
