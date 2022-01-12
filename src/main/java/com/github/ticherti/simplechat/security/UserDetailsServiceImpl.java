package com.github.ticherti.simplechat.security;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("Authenticating '{}'", login);
        Optional<User> optionalUser = userRepository.findByLogin(login);
        return new AuthUser(optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User '" + login + "' was not found")));
    }
}