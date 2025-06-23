package com.example.bcsd.dto;

import com.example.bcsd.domain.Article;

import java.time.LocalDateTime;

public class ArticleResponseDto {
    private Long id;
    private Long writerId;
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.writerId = article.getWriter().getId();
        this.boardId = article.getBoard().getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdDate = article.getCreatedDate();
        this.modifiedDate = article.getModifiedDate();
    }

    public Long getId() {
        return id;
    }

    public Long getWriterId() {
        return writerId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}