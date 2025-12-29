package com.example.bcsd.repository;

import com.example.bcsd.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT COUNT(a) FROM Article a WHERE a.board.id = :boardId")
    int countArticlesByBoard(@Param("boardId") Long boardId);

    @Query("SELECT DISTINCT b FROM Board b " + "LEFT JOIN FETCH b.articles")
    List<Board> findAllWithArticles();

    @Query("SELECT DISTINCT b FROM Board b " + "LEFT JOIN FETCH b.articles a " + "LEFT JOIN FETCH a.writer")
    List<Board> findAllWithArticlesAndWriter();
}
