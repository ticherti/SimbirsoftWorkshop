package com.github.ticherti.simplechat.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveRequestMessageTo {

    private Long roomId;
    private Long userId;
    private String content;
}
