package com.github.ticherti.simplechat.repository;

import com.github.ticherti.simplechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
//
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM Messages m WHERE m.user_id=?1; DELETE FROM Users u WHERE u.id=?1", nativeQuery = true)
//    int delete(long id);
}
