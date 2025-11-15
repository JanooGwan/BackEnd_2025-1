package com.example.bcsd.model;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private Long writerId;
    private Long boardId;
    private String title;
    private String content;
    private final LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = null;
    }

    public Article(Long writerId, Long boardId, String title, String content) {
        this.writerId = writerId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = null;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
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

    public void setId(Long id) {
        this.id = id;
    }
}
