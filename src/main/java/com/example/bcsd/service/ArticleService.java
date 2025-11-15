package com.example.bcsd.service;

import com.example.bcsd.domain.Article;
import com.example.bcsd.controller.dto.ArticleCreateRequestDto;
import com.example.bcsd.controller.dto.ArticleResponseDto;
import com.example.bcsd.controller.dto.ArticleUpdateRequestDto;
import com.example.bcsd.repository.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepository();


    public ArticleResponseDto getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponseDto::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 게시글을 찾을 수 없습니다."));
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

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID에 해당하는 게시글이 존재하지 않습니다.")
                );

        articleRepository.deleteById(id);
    }
}
