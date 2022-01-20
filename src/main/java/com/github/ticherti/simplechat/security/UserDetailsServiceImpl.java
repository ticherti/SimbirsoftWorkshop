package com.github.ticherti.simplechat.security;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException("User '" + login + "' was not found"));

        if (user.getEndBanTime().before(Timestamp.valueOf(LocalDateTime.now()))) {
            user.setActive(true);
        }
        return new AuthUser(user);
    }
}