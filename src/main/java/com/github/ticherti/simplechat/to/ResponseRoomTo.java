package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseRoomTo {
    private long id;
    private String name;
    private Long userId;
    private List<ResponseUserTo> users;
    private boolean isPrivate;

}


