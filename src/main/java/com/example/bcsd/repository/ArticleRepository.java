package com.example.bcsd.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.bcsd.domain.Article;

public class ArticleRepository {

    List<Article> articles = new ArrayList<>();

    public Optional<Article> findById(Long id) {
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
    }

    public Article save(Article article) {
        articles.add(article);
        return article;
    }

    public Article update(Long id, Article article) {
        Optional<Article> existingArticle = findById(id);

        if (existingArticle.isPresent()) {
            articles.remove(existingArticle.get());
            articles.add(article);
            return article;
        }

        return null;
    }

    public Article deleteById(Long id) {
        Optional<Article> existingArticle = findById(id);

        if (existingArticle.isPresent()) {
            Article article = existingArticle.get();
            articles.remove(article);
            return article;
        }

        return null;
    }

}