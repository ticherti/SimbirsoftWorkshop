package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.Role;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.UserNotFoundException;
import com.github.ticherti.simplechat.mapper.UserMapper;
import com.github.ticherti.simplechat.repository.UserRepository;
import com.github.ticherti.simplechat.to.ResponseUserDTO;
import com.github.ticherti.simplechat.to.SaveRequestUserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseUserDTO save(SaveRequestUserDTO requestUserTo) {
        log.info("Saving user");
        User user = userMapper.toEntity(requestUserTo);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(requestUserTo.getPassword()));
        user.setActive(true);
        user.setEndBanTime(Timestamp.valueOf(LocalDateTime.now()));
        return userMapper.toTO(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDTO> readAll() {
        return userMapper.allToTOs(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseUserDTO read(long id) {
        log.info("Reading user");
        return userMapper.toTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Transactional
    public ResponseUserDTO update(SaveRequestUserDTO userTO, User user) {
//        todo no xss validation, not here nor in save()
        log.info("Updating a user from TO");
        user.setPassword(passwordEncoder.encode(userTO.getPassword()));
        user.setLogin(userTO.getLogin());
        return userMapper.toTO(userRepository.save(user));
    }

    @Transactional
    public void delete(long id) {
        log.info("Deleting user");
        User deletedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        deletedUser.getRooms().forEach(room -> room.getUsers().remove(deletedUser));
        log.info("Deleting user after cleaning rooms");
        userRepository.deleteById(id);
    }

    @Transactional
    public void ban(long id, boolean isActive, Integer minutes) {
        log.info("Enabling {}", isActive);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(isActive);
        user.setEndBanTime(Timestamp.valueOf(minutes == null ?
                LocalDateTime.of(9999, 01, 01, 00, 00) :
                LocalDateTime.now().plusMinutes(minutes)));
    }

    @Transactional
    public void setModerator(long id, String role) {
        log.info("Setting moderator");
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setRole(Role.valueOf(role));
    }
//    todo Authentication getPrincipal problem
}
