package com.example.bcsd.controller.dto.request;

import com.example.bcsd.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardCreateRequest(

        @NotBlank(message = "게시판 이름은 필수입니다.")
        @Size(min = 1, max = 20, message = "게시판 이름은 1~20자 사이여야 합니다.")
        String name
) {
    public Board toEntity() {
        return new Board(name);
    }
}
