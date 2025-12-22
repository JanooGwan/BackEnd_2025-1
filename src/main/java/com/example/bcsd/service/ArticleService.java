package com.example.bcsd.service;

import com.example.bcsd.controller.dto.response.ArticleViewResponse;
import com.example.bcsd.controller.dto.response.ArticlesInBoardViewResponse;
import com.example.bcsd.controller.dto.request.ArticleCreateRequest;
import com.example.bcsd.controller.dto.response.ArticleResponse;
import com.example.bcsd.controller.dto.request.ArticleUpdateRequest;
import com.example.bcsd.entity.Article;
import com.example.bcsd.entity.Board;
import com.example.bcsd.entity.Member;
import com.example.bcsd.global.exception.CustomException;
import com.example.bcsd.global.exception.ErrorCode;
import com.example.bcsd.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository,
                          MemberRepository memberRepository,
                          BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticlesByBoardId(Long boardId) {
        return articleRepository.findAllByBoardId(boardId)
                .stream()
                .map(ArticleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleViewResponse> getArticlesByBoardWithWriter(Long boardId) {
        return articleRepository.findAllByBoardId(boardId)
                .stream()
                .map(article -> {
                    String writerName = memberRepository.findById(article.getWriterId())
                            .map(Member::getName)
                            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

                    return ArticleViewResponse.of(article, writerName);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ArticlesInBoardViewResponse> getBoardsWithArticles() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(board -> {
                    var articles = getArticlesByBoardWithWriter(board.getId());
                    return ArticlesInBoardViewResponse.of(board.getId(), board.getName(), articles);
                })
                .toList();
    }

    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest requestDto) {

        memberRepository.findById(requestDto.writerId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_MEMBER_REFERENCE));

        boardRepository.findById(requestDto.boardId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BOARD_REFERENCE));

        Article article = articleRepository.save(requestDto.toEntity());
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, ArticleUpdateRequest requestDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        article.update(requestDto.title(), requestDto.content());
        return ArticleResponse.from(articleRepository.update(id, article));
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        articleRepository.deleteById(id);
    }
}
