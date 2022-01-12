package com.github.ticherti.simplechat.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserDTO {
    @NotNull(message = "Id mustn't be null")
    private long id;

    @NotBlank(message = "login must from 6 to 20 characters")
    @Size(min = 3, max = 30)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password must from 6 to 20 characters")
    @Size(min = 6, max = 20)
    private String password;

    @NotNull(message = "Role mustn't be null")
    private Role role;

    private boolean isActive;
    private Timestamp startBanTime;
    private Timestamp endBanTime;
}
