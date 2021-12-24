package com.github.ticherti.simplechat.to;

import com.github.ticherti.simplechat.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserTo {
    private long id;
    private String login;
    private String password;
    private Role role;
    //    private List<ResponseRoomTo> rooms;
    private boolean isActive;
    private Timestamp startBanTime;
    private Timestamp endBanTime;
}
