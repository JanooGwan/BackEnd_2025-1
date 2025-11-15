package com.example.bcsd.controller.dto.response;

import java.util.List;

public record ArticlesInBoardViewResponse (
    Long boardId,
    String boardName,
    List<ArticleViewResponse> articles
) {
    public static ArticlesInBoardViewResponse of(Long boardId, String boardName, List<ArticleViewResponse> articles) {
        return new ArticlesInBoardViewResponse(
            boardId, boardName, articles);
    }
}
