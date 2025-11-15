package com.example.bcsd.service;

import com.example.bcsd.controller.dto.response.ArticleViewResponse;
import com.example.bcsd.model.Article;
import com.example.bcsd.model.Member;
import com.example.bcsd.controller.dto.request.ArticleCreateRequest;
import com.example.bcsd.controller.dto.response.ArticleResponse;
import com.example.bcsd.controller.dto.request.ArticleUpdateRequest;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleResponse::from)
                .toList();
    }

    public ArticleResponse getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 게시글이 존재하지 않습니다."));
    }

    public List<ArticleResponse> getArticlesByBoardId(Long boardId) {
        return articleRepository.findAllByBoardId(boardId).stream()
                .map(ArticleResponse::from)
                .toList();
    }

    public List<ArticleViewResponse> getArticlesByBoardWithWriter(Long boardId) {
        return articleRepository.findAllByBoardId(boardId).stream()
                .map(article -> {
                    String writerName = memberRepository.findById(article.getWriterId())
                            .map(Member::getName)
                            .orElseThrow(() ->
                                    new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 작성한 회원이 존재하지 않습니다.")
                            );

                    return new ArticleViewResponse(
                            article.getId(),
                            article.getTitle(),
                            article.getContent(),
                            writerName,
                            article.getCreatedAt(),
                            article.getModifiedAt()
                    );
                })
                .toList();
    }


    public ArticleResponse createArticle(ArticleCreateRequest requestDto) {
        Article article = articleRepository.save(requestDto.toEntity());
        return ArticleResponse.from(article);
    }

    public ArticleResponse updateArticle(Long id, ArticleUpdateRequest requestDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 게시글이 존재하지 않습니다.")
                );

        article.update(requestDto.title(), requestDto.content());
        Article updatedArticle = articleRepository.update(id, article);

        return ArticleResponse.from(updatedArticle);
    }


    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 게시글이 존재하지 않습니다.")
                );

        articleRepository.deleteById(id);
    }
}
