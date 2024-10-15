package com.bravos.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsInfo {

    private String id;
    private String title;
    private String content;
    private String image;

}
