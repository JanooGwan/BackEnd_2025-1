package com.example.bcsd.repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import com.example.bcsd.model.Article;

public class ArticleRepository {

    private final AtomicLong key = new AtomicLong(0);
    private final Map<Long, Article> articles = new HashMap<>();

    public Optional<Article> findById(Long id) {
        Article article = articles.get(id);

        return Optional.ofNullable(article);
    }

    public Article save(Article article) {
        article.setId(key.incrementAndGet());
        articles.put(key.get(), article);

        return article;
    }

    public Article update(Long id, Article article) {
        articles.put(id, article);

        return article;
    }

    public void deleteById(Long id) {
        articles.remove(id);
    }
}
