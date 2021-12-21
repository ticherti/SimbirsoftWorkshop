package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseRoomDTO {
    private long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    private ResponseUserDTO creator;

    private List<ResponseUserDTO> users;

    @NotNull
    private boolean isPrivate;

}


