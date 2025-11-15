package com.example.bcsd.controller.dto.response;

import java.time.LocalDateTime;

public record ArticleViewResponse(
        Long id,
        String title,
        String content,
        String writerName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
