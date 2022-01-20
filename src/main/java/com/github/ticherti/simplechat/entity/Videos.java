package com.github.ticherti.simplechat.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Videos {
    private String id;
    private String title;
    private Date published;
    private long viewCount;
    private long likeCount;
}
