package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.service.UserService;
import com.github.ticherti.simplechat.to.ResponseUserDTO;
import com.github.ticherti.simplechat.to.SaveRequestUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.ticherti.simplechat.mapper.UserMapper.userMapper;

@RestController
@RequestMapping(value = "rest/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUserDTO create(@RequestBody SaveRequestUserDTO userTo) {
        User user = userService.save(userMapper.toEntity(userTo));
//        todo Add not null check, probably in services
        if (userTo == null) {
            throw new NullUserException();
        }
        return userMapper.toTO(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseUserDTO read(@PathVariable long id) {
//        todo Check consistency somehow
        return userMapper.toTO(userService.read(id));
    }

    @GetMapping("")
    public List<ResponseUserDTO> readAll() {
//        todo Check consistency somehow
        return userMapper.allToTOs(userService.readAll());
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody ResponseUserDTO userTo) {
//        todo Check consistency and probably not found case
//        Find out if I need to get id in the parameters
        if (userTo == null) {
            throw new NullUserException();
        }
        User user = userService.update(userMapper.toEntity(userTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
}
