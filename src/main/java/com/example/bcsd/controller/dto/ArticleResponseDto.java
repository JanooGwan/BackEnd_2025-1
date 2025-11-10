package com.example.bcsd.controller.dto;

import com.example.bcsd.domain.Article;

import java.time.LocalDateTime;

public record ArticleResponseDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
    public static ArticleResponseDto from(Article article) {
        return new ArticleResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedDate(),
                article.getModifiedDate()
        );
    }
}