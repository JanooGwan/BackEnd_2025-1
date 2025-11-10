package com.example.bcsd.service;

import com.example.bcsd.domain.Article;
import com.example.bcsd.controller.dto.ArticleCreateRequestDto;
import com.example.bcsd.controller.dto.ArticleResponseDto;
import com.example.bcsd.controller.dto.ArticleUpdateRequestDto;
import com.example.bcsd.repository.ArticleRepository;

import java.util.Optional;

public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepository();


    public Optional<ArticleResponseDto> getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponseDto::from);
    }

    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        Article article = articleRepository.save(requestDto.toEntity());
        return ArticleResponseDto.from(article);
    }

    public Optional<ArticleResponseDto> updateArticle(Long id, ArticleUpdateRequestDto requestDto) {
        Optional<Article> existingArticle = articleRepository.findById(id);

        if (existingArticle.isPresent()) {
            Article article = existingArticle.get();
            article.update(requestDto.title(), requestDto.content());
            return Optional.of(ArticleResponseDto.from(article));
        }

        return Optional.empty();
    }

    public Optional<ArticleResponseDto> deleteArticle(Long id) {
        Optional<Article> article = Optional.ofNullable(articleRepository.deleteById(id));
        --Article.articleCount;
        return article.map(ArticleResponseDto::from);
    }
}
