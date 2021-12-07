package com.github.ticherti.simplechat.to;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseUserTo {
    private long id;
    private String login;
    private String password;
    private Role role;
    private List<ResponseRoomTo> rooms;
    private boolean isBanned;
    private Timestamp startBanTime;
    private Timestamp endBanTime;
}
