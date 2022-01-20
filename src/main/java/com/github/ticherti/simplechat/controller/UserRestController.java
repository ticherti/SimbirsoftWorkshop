package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.security.AuthUser;
import com.github.ticherti.simplechat.service.UserService;
import com.github.ticherti.simplechat.to.ResponseUserDTO;
import com.github.ticherti.simplechat.to.SaveRequestUserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "rest/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUserDTO create(@Valid @RequestBody SaveRequestUserDTO userTo) {
        log.info("creating a user");
        return userService.save(userTo);
    }

    @GetMapping("/profile")
    public ResponseUserDTO getProfile(@AuthenticationPrincipal AuthUser user) {
        log.info("Getting user's profile with id " + user.id());
        return userService.read(user.id());
    }

    @GetMapping("/{id}")
    public ResponseUserDTO read(@NotNull @PathVariable long id) {
        log.info("Getting a user " + id);
        return userService.read(id);
    }

    @GetMapping
    public List<ResponseUserDTO> readAll() {
        log.info("Getting all users");
        return userService.readAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody SaveRequestUserDTO userTo, @AuthenticationPrincipal AuthUser user) {
        log.info("Updating a user " + user.id());
//        todo Think about adding sorting everywhere
        userService.update(userTo, user.getUser());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser user) {
        log.info("deleting user " + user.id());
        userService.delete(user.id());
    }
}
