package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseRoomTo {
    private long id;
    private String name;
    private ResponseUserTo creator;
    private List<ResponseUserTo> users;
    private boolean isPrivate;

}


