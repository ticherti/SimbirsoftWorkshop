package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SaveRequestRoomTo {

    private String name;
    private Long userId;
    private boolean isPrivate;
}


