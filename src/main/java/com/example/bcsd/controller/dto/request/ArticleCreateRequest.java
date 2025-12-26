package com.example.bcsd.controller.dto.request;

import com.example.bcsd.entity.Article;
import com.example.bcsd.entity.Board;
import com.example.bcsd.entity.Member;
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
    public Article toEntity(Member writer, Board board) {
        return new Article(writer, board, title, content);
    }
}
