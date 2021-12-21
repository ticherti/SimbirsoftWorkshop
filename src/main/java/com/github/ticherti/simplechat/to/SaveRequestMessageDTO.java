package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveRequestMessageDTO {
    @NotNull
    private SaveRequestRoomDTO room;

    @NotNull
    private SaveRequestUserDTO user;

    @NotBlank
    @Size(max = 1000)
    private String content;
}
