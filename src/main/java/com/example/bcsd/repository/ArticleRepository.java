package com.example.bcsd.repository;

import com.example.bcsd.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByBoardId(Long boardId);

    @Query("SELECT a FROM Article a " + "JOIN FETCH a.writer " + "JOIN FETCH a.board")
    List<Article> findAllWithWriterAndBoard();

    @Query("SELECT a FROM Article a " + "JOIN FETCH a.writer " + "WHERE a.board.id = :boardId")
    List<Article> findAllByBoardIdWithWriter(@Param("boardId") Long boardId);

    @Query("SELECT a FROM Article a " + "JOIN FETCH a.writer " + "JOIN FETCH a.board " + "WHERE a.board.id = :boardId")
    List<Article> findAllByBoardIdWithWriterAndBoard(@Param("boardId") Long boardId);

    @Query("SELECT a FROM Article a " + "JOIN FETCH a.writer " + "JOIN FETCH a.board " + "WHERE a.id = :id")
    Optional<Article> findByIdWithWriterAndBoard(@Param("id") Long id);

    @Query("SELECT a FROM Article a " + "JOIN FETCH a.writer")
    List<Article> findAllWithWriter();
}
