package com.example.bcsd.controller.dto.response;

import com.example.bcsd.model.Board;

public record BoardResponse(
        Long id,
        String name
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getName()
        );
    }
}
