package com.bravos.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsItem {

    private UUID id;
    private String title;
    private String image;
    private Date postedDate;
    private String authorName;

}
