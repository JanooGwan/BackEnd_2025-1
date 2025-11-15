package com.example.bcsd.repository;

import com.example.bcsd.model.Article;
import com.example.bcsd.model.Board;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BoardRepository {
    private final AtomicLong key = new AtomicLong(0);
    private final Map<Long, Board> boards = new HashMap<>();

    public List<Board> findAll() {
        List<Board> result = new ArrayList<>();
        boards.forEach((id, board) -> result.add(board));

        return result;
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
