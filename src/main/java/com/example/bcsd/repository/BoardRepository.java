package com.example.bcsd.repository;

import com.example.bcsd.model.Board;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class BoardRepository {
    private final AtomicLong key = new AtomicLong(0);
    private final Map<Long, Board> boards = new HashMap<>();

    public Optional<Board> findById(Long id) {
        Board board = boards.get(id);

        return Optional.ofNullable(board);
    }

    public Board save(Board board) {
        board.setId(key.incrementAndGet());
        boards.put(key.get(), board);

        return board;
    }

    public Board update(Long id, Board board) {
        boards.put(id, board);

        return board;
    }

    public void deleteById(Long id) {
        boards.remove(id);
    }
}
