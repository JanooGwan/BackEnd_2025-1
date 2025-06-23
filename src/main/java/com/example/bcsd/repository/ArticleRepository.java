package com.example.bcsd.repository;


import com.example.bcsd.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByBoardId(Long boardId);
    boolean existsByWriter_Id(Long id);
    boolean existsByBoard_Id(Long id);
}