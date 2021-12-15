package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseMessageTo {
    private long id;
    private ResponseRoomTo room;
    private ResponseUserTo user;
    private String content;
    private Timestamp dateTime;
}
