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
    @NotNull(message = "Id mustn't be null")
    private long id;

    @NotNull(message = "Room mustn't be null")
    private ResponseRoomDTO room;

    @NotNull(message = "User mustn't be null")
    private ResponseUserDTO user;

    @NotBlank(message = "Text must be less than 1000 characters")
    @Size(max = 1000)
    private String content;
    private Timestamp creationDateTime;
}
