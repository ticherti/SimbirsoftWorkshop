package com.github.ticherti.simplechat.dto;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveRequestUserDTO {

    private String login;
    private String password;
    private Role role;
}
