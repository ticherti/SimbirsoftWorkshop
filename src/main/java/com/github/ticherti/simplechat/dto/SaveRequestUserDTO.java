package com.github.ticherti.simplechat.dto;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SaveRequestUserDTO {

    private String login;
    private String password;
    private Role role;
}
