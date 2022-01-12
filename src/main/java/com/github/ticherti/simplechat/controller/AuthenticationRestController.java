package com.github.ticherti.simplechat.controller;


import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.repository.UserRepository;
import com.github.ticherti.simplechat.security.JwtTokenProvider;
import com.github.ticherti.simplechat.to.AuthenticationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rest/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            log.info(request.getLogin() + " " + request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
            User user = userRepository.findByLogin(request.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(request.getLogin(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", request.getLogin());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}