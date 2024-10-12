package com.bravos.news.dto;

import com.bravos.news.entity.News;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsThread {

    private News news;
    private String authorName;

}
