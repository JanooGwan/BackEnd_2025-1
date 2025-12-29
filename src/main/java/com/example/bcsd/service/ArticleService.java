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
        return articleRepository.findAllWithWriterAndBoard()
                .stream()
                .map(ArticleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        return articleRepository.findByIdWithWriterAndBoard(id)
                .map(ArticleResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticlesByBoardId(Long boardId) {
        return articleRepository.findAllByBoardIdWithWriterAndBoard(boardId)
                .stream()
                .map(ArticleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleViewResponse> getArticlesByBoardWithWriter(Long boardId) {
        return articleRepository.findAllByBoardIdWithWriter(boardId)
                .stream()
                .map(article -> ArticleViewResponse.of(article, article.getWriter().getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ArticlesInBoardViewResponse> getBoardsWithArticles() {
        List<Board> boards = boardRepository.findAllWithArticlesAndWriter();

        return boards.stream()
                .map(board -> {
                    List<ArticleViewResponse> articles = board.getArticles().stream()
                            .map(article -> ArticleViewResponse.of(article, article.getWriter().getName()))
                            .toList();
                    return ArticlesInBoardViewResponse.of(board.getId(), board.getName(), articles);
                })
                .toList();
    }

    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest requestDto) {

        Member writer = memberRepository.findById(requestDto.writerId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_MEMBER_REFERENCE));

        Board board = boardRepository.findById(requestDto.boardId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BOARD_REFERENCE));

        Article article = articleRepository.save(requestDto.toEntity(writer, board));
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, ArticleUpdateRequest requestDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        article.update(requestDto.title(), requestDto.content());

        return ArticleResponse.from(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        articleRepository.deleteById(id);
    }
}
