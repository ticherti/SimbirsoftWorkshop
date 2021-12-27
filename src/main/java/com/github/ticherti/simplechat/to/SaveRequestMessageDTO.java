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
    @NotNull(message = "Room mustn't be null")
    private Long roomId;
    @NotNull(message = "User mustn't be null")
    private Long userId;

    @NotBlank(message = "Text must be less than 1000 characters")
    @Size(max = 1000)
    private String content;
}
