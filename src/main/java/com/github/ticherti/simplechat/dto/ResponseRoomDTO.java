package com.github.ticherti.simplechat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseRoomDTO {
    private long id;
    private String name;
    private ResponseUserDTO creator;
    private List<ResponseUserDTO> users;
    private boolean isPrivate;

}


