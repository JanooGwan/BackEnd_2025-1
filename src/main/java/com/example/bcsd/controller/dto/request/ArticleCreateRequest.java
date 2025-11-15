package com.example.bcsd.controller.dto.request;

import com.example.bcsd.model.Article;

public record ArticleCreateRequest(
        Long writerId,
        Long boardId,
        String title,
        String content
) {
    public Article toEntity() {
        return new Article(writerId, boardId, title, content);
    }
}
