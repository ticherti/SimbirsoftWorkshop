package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.exception.NullUserException;
import com.github.ticherti.simplechat.service.UserService;
import com.github.ticherti.simplechat.to.ResponseUserDTO;
import com.github.ticherti.simplechat.to.SaveRequestUserDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "rest/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {
    private static final Logger log = getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUserDTO create(@Valid @RequestBody SaveRequestUserDTO userTo) {
        log.info("creating a user");
//        todo Extend entities from base abstract. Refactor users classes, extract null checks.
        if (userTo == null) {
            throw new NullUserException();
        }
        return userService.save(userTo);
    }

    @GetMapping("/{id}")
    public ResponseUserDTO read(@NotNull @PathVariable long id) {
        log.info("Getting a user " + id);
        return userService.read(id);
    }

    @GetMapping("")
    public List<ResponseUserDTO> readAll() {
        log.info("Getting all users");
        return userService.readAll();
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ResponseUserDTO userTo) {
        log.info("Updating a user " + userTo.getId());
//        todo Find out if I need to get id in the parameters for consistency  and security check
        if (userTo == null) {
            throw new NullUserException();
        }
        userService.update(userTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NotNull @PathVariable long id) {
        log.info("deleting user " + id);
        userService.delete(id);
    }
}
