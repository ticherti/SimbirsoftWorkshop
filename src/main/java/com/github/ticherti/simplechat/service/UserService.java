package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Role;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.RoomNotFoundException;
import com.github.ticherti.simplechat.exception.UserNotFoundException;
import com.github.ticherti.simplechat.mapper.UserMapper;
import com.github.ticherti.simplechat.repository.UserRepository;
import com.github.ticherti.simplechat.to.ResponseUserDTO;
import com.github.ticherti.simplechat.to.SaveRequestUserDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    //    todo Check out if passwordEncoder is ok to be here
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseUserDTO save(SaveRequestUserDTO requestUserTo) {
        log.info("Saving user");
        User user = userMapper.toEntity(requestUserTo);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(requestUserTo.getPassword()));
        user.setActive(true);
        return userMapper.toTO(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDTO> readAll() {
        return userMapper.allToTOs(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseUserDTO read(long id) {
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
    public ResponseUserDTO update(ResponseUserDTO responseUserTo) {
//        todo probably login should be changeable
//        todo Decide something with updation of users created rooms
        Optional<User> existedUser = userRepository.findById(responseUserTo.getId());
        long id = responseUserTo.getId();
        if (existedUser.isPresent()) {
            User user = userMapper.toEntity(responseUserTo);
            user.setId(id);
            return userMapper.toTO(userRepository.save(user));
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void delete(long id) {
//        todo Should decide something to delete created rooms. Or change creator field in Room to nullable.
        log.info("Deleting user");
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new RoomNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
