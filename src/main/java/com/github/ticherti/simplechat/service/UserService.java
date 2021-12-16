package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.UserNotFoundException;
import com.github.ticherti.simplechat.mapper.UserMapper;
import com.github.ticherti.simplechat.repository.UserRepository;
import com.github.ticherti.simplechat.to.ResponseUserTo;
import com.github.ticherti.simplechat.to.SaveRequestUserTo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class UserService {
    private static final Logger log = getLogger(UserService.class);

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Transactional
    public ResponseUserTo save(SaveRequestUserTo requestUserTo) {
        log.info("Saving user");
        return userMapper.toTO(userRepository.save(userMapper.toEntity(requestUserTo)));
    }

    @Transactional(readOnly = true)
    public List<ResponseUserTo> readAll() {
        return userMapper.allToTOs(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseUserTo read(long id) {
//        There is no privacy check here
        log.info("Reading user");
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.toTO(user.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    public ResponseUserTo update(ResponseUserTo responseUserTo) {
        Optional<User> existedUser = userRepository.findById(responseUserTo.getId());
        long id = responseUserTo.getId();
        if (existedUser.isPresent()) {
            User user = userMapper.toEntity(responseUserTo);
            user.setId(id);
            return userMapper.toTO(user);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void delete(long id) {
        log.info("Deleting user");
        if (userRepository.delete(id) == 0) {
            throw new UserNotFoundException(id);
        }
    }
}
