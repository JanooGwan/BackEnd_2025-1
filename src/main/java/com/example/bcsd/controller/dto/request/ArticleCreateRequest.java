package com.example.bcsd.controller.dto.request;

import com.example.bcsd.entity.Article;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticleCreateRequest(
        @NotNull
        Long writerId,
        @NotNull
        Long boardId,
        @NotBlank
        String title,
        @NotBlank
        String content
) {
    public Article toEntity() {
        return new Article(writerId, boardId, title, content);
    }
}
