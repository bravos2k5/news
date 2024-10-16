package com.bravos.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsAdminRequest {
    private String id;
    private String title;
    private String content;
    private String image;
    private String categoryId;
    private boolean imgStatus;
    private String home;
}
