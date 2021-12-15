package com.github.ticherti.simplechat.dto;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserDTO {
    private long id;
    private String login;
    private String password;
    private Role role;
    private List<ResponseRoomDTO> rooms;
    private boolean isBanned;
    private Timestamp startBanTime;
    private Timestamp endBanTime;
}
