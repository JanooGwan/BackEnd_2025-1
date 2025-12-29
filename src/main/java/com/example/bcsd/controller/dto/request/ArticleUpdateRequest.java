package com.example.bcsd.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArticleUpdateRequest(

        @NotBlank(message = "제목은 필수입니다.")
        @Size(min = 1, max = 100, message = "제목은 1~100자 사이여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        @Size(min = 1, max = 10000, message = "내용은 1~10000자 사이여야 합니다.")
        String content
) {
}
