package com.example.bcsd.dto;

import java.util.List;

public class ArticleListResponseDto {

    private String boardName;
    private List<ArticleResponseDto> articles;

    public ArticleListResponseDto(String boardName, List<ArticleResponseDto> articles) {
        this.boardName = boardName;
        this.articles = articles;
    }

    public String getBoardName() {
        return boardName;
    }

    public List<ArticleResponseDto> getArticles() {
        return articles;
    }
}
