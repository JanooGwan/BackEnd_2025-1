package com.example.bcsd.controller.dto.request;

import com.example.bcsd.model.Article;

public record ArticleCreateRequest(
        String title,
        String content
) {
    public Article toEntity() {
        return new Article(title, content);
    }
}
