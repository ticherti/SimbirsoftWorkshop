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
    @NotNull(message = "Id mustn't be null")
    private long id;

    @NotBlank(message = "Name must be from 3 to 20 characters")
    @Size(min = 3, max = 20)
    private String name;

    @NotNull(message = "Creater's Id mustn't be null")
    private Long userId;

    private List<ResponseUserDTO> users;

    @NotNull(message = "Private boolean field mustn't be null")
    private boolean isPrivate;

}


