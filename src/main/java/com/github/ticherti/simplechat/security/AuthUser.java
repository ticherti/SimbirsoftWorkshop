package com.github.ticherti.simplechat.security;

import com.github.ticherti.simplechat.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getLogin(), user.getPassword(),
                user.isActive(), user.isActive(), user.isActive(), user.isActive(),
                user.getRole().getAuthorities());
        this.user = user;
    }

    public long id() {
        return user.getId();
    }
}