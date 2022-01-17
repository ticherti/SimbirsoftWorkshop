package com.github.ticherti.simplechat.controller;

import com.github.ticherti.simplechat.security.AuthUser;
import com.github.ticherti.simplechat.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Log4j2
@RestController
@RequestMapping(value = "rest/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController {
    @Autowired
    private UserService userService;
//todo Something said in future about renaming for admins

    @PatchMapping("/{id}/ban")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ban(@PathVariable long id, @RequestParam boolean isActive, @Nullable @RequestParam Integer minutes,
                    @AuthenticationPrincipal AuthUser currentUser) {
        log.info(isActive ? "enable {}" : "disable {}", id);
        userService.ban(id, isActive, minutes, currentUser);
    }

    @PatchMapping("/{id}/moderator")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setModerator(@PathVariable long id, @RequestParam String role) {
        log.info("Setting role to user with id {}, role is {}", id, role);
        userService.setModerator(id, role.toUpperCase(Locale.ROOT));
    }
}
