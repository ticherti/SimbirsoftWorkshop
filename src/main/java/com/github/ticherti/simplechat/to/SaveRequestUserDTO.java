package com.github.ticherti.simplechat.to;

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
    @NotBlank(message = "login must from 6 to 20 characters")
    @Size(min = 6, max = 20)
    private String login;

    @NotBlank(message = "Password must from 6 to 20 characters")
    @Size(min = 6, max = 20)
    private String password;
}
