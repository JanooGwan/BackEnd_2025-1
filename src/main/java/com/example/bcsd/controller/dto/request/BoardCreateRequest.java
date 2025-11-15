package com.example.bcsd.controller.dto.request;

import com.example.bcsd.model.Board;

public record BoardCreateRequest(
        String name
) {
    public Board toEntity() { return new Board(name); }
}
