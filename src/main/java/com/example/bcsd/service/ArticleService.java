package com.example.bcsd.service;

import com.example.bcsd.controller.dto.response.ArticleViewResponse;
import com.example.bcsd.controller.dto.response.ArticlesInBoardViewResponse;
import com.example.bcsd.model.Article;
import com.example.bcsd.model.Board;
import com.example.bcsd.model.Member;
import com.example.bcsd.controller.dto.request.ArticleCreateRequest;
import com.example.bcsd.controller.dto.response.ArticleResponse;
import com.example.bcsd.controller.dto.request.ArticleUpdateRequest;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleResponse::from)
                .toList();
    }

    public ArticleResponse getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponse::from)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "ID에 해당하는 게시글이 존재하지 않습니다."));
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
                                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                                            "게시글 작성자 정보가 존재하지 않습니다."));

                    return ArticleViewResponse.of(article, writerName);
                })
                .toList();
    }

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
        Article article = articleRepository.save(requestDto.toEntity());
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, ArticleUpdateRequest requestDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "ID에 해당하는 게시글이 존재하지 않습니다."));

        article.update(requestDto.title(), requestDto.content());
        return ArticleResponse.from(articleRepository.update(id, article));
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "ID에 해당하는 게시글이 존재하지 않습니다."));

        articleRepository.deleteById(id);
    }
}
