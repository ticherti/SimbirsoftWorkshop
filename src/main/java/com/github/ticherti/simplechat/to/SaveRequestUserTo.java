package com.github.ticherti.simplechat.to;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveRequestUserTo {
//todo probably should delete Role from this DTO
    private String login;
    private String password;
    private Role role;
}
