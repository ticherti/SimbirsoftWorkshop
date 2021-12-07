package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class SaveRequestMessageTo {

    private SaveRequestRoomTo room;
    private SaveRequestUserTo user;
    private String content;
    //    todo Not sure how it will work. Probably better to delete the field for the reason that rest api won't send time,
//    but it will be created at the DB
    private Timestamp dateTime;
}
