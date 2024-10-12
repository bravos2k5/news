package com.bravos.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsAdmin {

    private String id;
    private String title;
    private String categoryName;
    private int categoryId;
    private String authorName;
    private String postedDate;
    private int viewCount;
    private boolean isHome;
    private String content;
    private String image;

}
