package com.example.bcsd.controller.dto.response;

import com.example.bcsd.domain.beforejpa.Article;

import java.time.LocalDateTime;

public record ArticleViewResponse(
        Long id,
        String title,
        String content,
        String writerName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleViewResponse of(Article article, String name) {
        return new ArticleViewResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                name,
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }
}
