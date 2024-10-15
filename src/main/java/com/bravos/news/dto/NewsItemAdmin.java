package com.bravos.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsItemAdmin {

    private String id;
    private String title;
    private Date postedDate;

}
