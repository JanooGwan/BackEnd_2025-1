package com.example.bcsd.repository;

import com.example.bcsd.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT COUNT(a) FROM Article a WHERE a.boardId = :boardId")
    int countArticlesByBoard(@Param("boardId") Long boardId);
}
