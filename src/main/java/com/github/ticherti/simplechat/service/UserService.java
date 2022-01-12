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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

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
        log.info("Reading user");
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.toTO(user.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    public ResponseUserDTO update(SaveRequestUserDTO userTO, User user) {
//        todo no xss validation, not here nor in save()
        log.info("Updating a user from TO");
        user.setPassword(passwordEncoder.encode(userTO.getPassword()));
        user.setLogin(userTO.getLogin());
//        todo find out what's up with token after the password changing
        return userMapper.toTO(userRepository.save(user));
    }

    @Transactional
    public void delete(long id) {
//        todo Should decide something to delete created rooms. Or change creator field in Room to nullable.
//        Upd. check it now.
        log.info("Deleting user");
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

    @Transactional
    public void banned(long id, boolean banned, int minutes) {
        log.info("Enabling {}", banned);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(banned);
        user.setEndBanTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(minutes)));
    }

    @Transactional
    public void setModerator(long id, String role) {
        log.info("Setting moderator");
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setRole(Role.valueOf(role));
    }
}
