package com.example.bcsd.controller.dto.response;

import com.example.bcsd.model.Article;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedDate(),
                article.getModifiedDate()
        );
    }
}
