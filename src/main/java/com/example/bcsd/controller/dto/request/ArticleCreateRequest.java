package com.example.bcsd.controller.dto.request;

import com.example.bcsd.entity.Article;
import com.example.bcsd.entity.Board;
import com.example.bcsd.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ArticleCreateRequest(

        @NotNull(message = "작성자 ID는 필수입니다.")
        @Positive(message = "작성자 ID는 양수여야 합니다.")
        Long writerId,

        @NotNull(message = "게시판 ID는 필수입니다.")
        @Positive(message = "게시판 ID는 양수여야 합니다.")
        Long boardId,

        @NotBlank(message = "제목은 필수입니다.")
        @Size(min = 1, max = 100, message = "제목은 1~100자 사이여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        @Size(min = 1, max = 10000, message = "내용은 1~10000자 사이여야 합니다.")
        String content
) {
    public Article toEntity(Member writer, Board board) {
        return Article.builder()
                .writer(writer)
                .board(board)
                .title(title)
                .content(content)
                .build();
    }
}
