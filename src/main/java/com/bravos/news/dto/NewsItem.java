package com.bravos.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;
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

    public NewsItem(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return Objects.equals(id, newsItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
