package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseMessageDTO {
    @NotNull
    private long id;

    @NotNull
    private ResponseRoomDTO room;

    @NotNull
    private ResponseUserDTO user;

    @NotBlank
    @Size(max = 1000)
    private String content;

    private Timestamp dateTime;
}
