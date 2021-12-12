package com.github.ticherti.simplechat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SaveRequestRoomDTO {

    private String name;
    private SaveRequestUserDTO creator;
    private boolean isPrivate;

}


