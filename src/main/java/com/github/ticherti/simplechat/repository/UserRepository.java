package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
//    @Override
//    @Modifying
//    @Transactional
//    @Query(value =
//            "DELETE FROM messages m WHERE m.user_id=:id ; DELETE FROM rooms_users ru WHERE ru.users_id=:id ;" +
//                    "DELETE FROM rooms WHERE creator_id=:id ; DELETE FROM users u WHERE u.id=:id ", nativeQuery = true)
//    void deleteById(@Param("id") Long id);
}
