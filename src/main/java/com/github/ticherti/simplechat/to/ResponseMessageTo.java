package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class ResponseMessageTo {
    private long id;
    private ResponseRoomTo room;
    private ResponseUserTo user;
    private String content;
    private Timestamp dateTime;
}
