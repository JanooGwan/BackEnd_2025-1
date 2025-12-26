package com.example.bcsd.repository;

import com.example.bcsd.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByBoardId(Long boardId);
}
