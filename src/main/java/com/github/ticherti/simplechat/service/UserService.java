package com.github.ticherti.simplechat.service;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public User read(long id) {
        return userRepository.getById(id);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    //    probably should return something
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
