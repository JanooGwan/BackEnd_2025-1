package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.ArticleCreateRequestDto;
import com.example.bcsd.controller.dto.ArticleResponseDto;
import com.example.bcsd.controller.dto.ArticleUpdateRequestDto;
import com.example.bcsd.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService = new ArticleService();

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ArticleResponseDto>> getArticle(@PathVariable("id") Long id) {
        Optional<ArticleResponseDto> response = articleService.getArticleById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleCreateRequestDto articleCreateRequestDto) {
        ArticleResponseDto response = articleService.createArticle(articleCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ArticleResponseDto>> updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateRequestDto articleUpdateRequestDto) {
        Optional<ArticleResponseDto> response = articleService.updateArticle(id, articleUpdateRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        articleService.deleteArticle(id);

        return ResponseEntity.noContent().build();
    }
}
