package com.example.bcsd.controller.dto.response;

import com.example.bcsd.domain.beforejpa.Article;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        Long writerId,
        Long boardId,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getWriterId(),
                article.getBoardId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }
}
