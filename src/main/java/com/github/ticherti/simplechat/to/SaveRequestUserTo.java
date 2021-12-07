package com.github.ticherti.simplechat.to;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SaveRequestUserTo {

    private String login;
    private String password;
    private Role role;
}
