package com.example.bcsd.controller.dto.request;

import com.example.bcsd.entity.beforejpa.Board;
import jakarta.validation.constraints.NotBlank;

public record BoardCreateRequest(
        @NotBlank
        String name
) {
    public Board toEntity() { return new Board(name); }
}
