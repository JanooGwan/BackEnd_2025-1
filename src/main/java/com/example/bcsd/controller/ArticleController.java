package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.request.ArticleCreateRequest;
import com.example.bcsd.controller.dto.response.ArticleResponse;
import com.example.bcsd.controller.dto.request.ArticleUpdateRequest;
import com.example.bcsd.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService = new ArticleService();

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        ArticleResponse response = articleService.getArticleById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleCreateRequest articleCreateRequestDto) {
        ArticleResponse response = articleService.createArticle(articleCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateRequest articleUpdateRequestDto) {
        ArticleResponse response = articleService.updateArticle(id, articleUpdateRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        articleService.deleteArticle(id);

        return ResponseEntity.noContent().build();
    }
}
