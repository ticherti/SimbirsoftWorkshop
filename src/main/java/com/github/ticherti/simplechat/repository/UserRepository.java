package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
