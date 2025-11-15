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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 게시글이 존재하지 않습니다."));
    }

    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        Article article = articleRepository.save(requestDto.toEntity());
        return ArticleResponseDto.from(article);
    }

    public ArticleResponseDto updateArticle(Long id, ArticleUpdateRequestDto requestDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 게시글이 존재하지 않습니다.")
                );

        article.update(requestDto.title(), requestDto.content());
        Article updatedArticle = articleRepository.update(id, article);

        return ArticleResponseDto.from(updatedArticle);
    }


    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 게시글이 존재하지 않습니다.")
                );

        articleRepository.deleteById(id);
    }
}
