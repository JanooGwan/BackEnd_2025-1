package com.example.bcsd.controller.dto;

import com.example.bcsd.domain.Article;

public record ArticleCreateRequestDto(
        String title,
        String content
) {
    public Article toEntity() {
        return new Article(title, content);
    }
}