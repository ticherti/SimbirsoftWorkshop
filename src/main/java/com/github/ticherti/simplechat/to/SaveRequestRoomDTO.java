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
public class SaveRequestRoomDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    private SaveRequestUserDTO creator;

    @NotNull
    private boolean isPrivate;
}


