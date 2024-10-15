package com.bravos.news.entity;

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
public class News {

    private UUID id;
    private String title;
    private String content;
    private String image;
    private UUID authorId;
    private Date postedDate;
    private Integer viewCount;
    private boolean isHome;
    private int categoryId;

    public News(UUID id) {
        this.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}