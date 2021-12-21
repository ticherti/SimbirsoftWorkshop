package com.github.ticherti.simplechat.to;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveRequestUserDTO {
    @NotBlank
    @Size(min = 6, max = 20)
    private String login;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    private Role role;
}
