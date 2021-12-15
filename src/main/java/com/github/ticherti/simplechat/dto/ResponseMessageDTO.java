package com.github.ticherti.simplechat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseMessageDTO {
    private long id;
    private ResponseRoomDTO room;
    private ResponseUserDTO user;
    private String content;
    private Timestamp dateTime;
}
