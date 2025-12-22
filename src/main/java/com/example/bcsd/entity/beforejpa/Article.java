package com.example.bcsd.entity.beforejpa;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class Article {

    private Long id;
    private Long writerId;
    private Long boardId;
    private String title;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt= LocalDateTime.now();
        this.modifiedAt = null;
    }

    public Article(Long writerId, Long boardId, String title, String content) {
        this.writerId = writerId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = null;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
