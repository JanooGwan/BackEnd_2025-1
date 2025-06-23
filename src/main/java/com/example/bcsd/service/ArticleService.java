package com.example.bcsd.service;

import com.example.bcsd.domain.Article;
import com.example.bcsd.domain.Board;
import com.example.bcsd.domain.Member;
import com.example.bcsd.dto.ArticleListResponseDto;
import com.example.bcsd.dto.ArticleRequestDto;
import com.example.bcsd.dto.ArticleResponseDto;
import com.example.bcsd.exception.*;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    public List<ArticleListResponseDto> getAllArticles() {
        Map<Long, List<Article>> grouped = articleRepository.findAll().stream()
                .collect(Collectors.groupingBy(a -> a.getBoard().getId()));

        return grouped.entrySet().stream()
                .map(entry -> {
                    Long boardId = entry.getKey();
                    String boardName = boardRepository.findById(boardId)
                            .map(Board::getName)
                            .orElse("알 수 없음");
                    List<ArticleResponseDto> responses = entry.getValue().stream()
                            .map(ArticleResponseDto::new)
                            .collect(Collectors.toList());
                    return new ArticleListResponseDto(boardName, responses);
                })
                .collect(Collectors.toList());
    }

    public ArticleResponseDto getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponseDto::new)
                .orElseThrow(() -> new ArticleNotFoundException(ErrorCode.CANNOT_FIND_ARTICLE));
    }

    public List<ArticleResponseDto> getArticlesByBoardId(Long boardId) {
        return articleRepository.findByBoardId(boardId).stream()
                .map(ArticleResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto dto) {
        Member writer = memberRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.CANNOT_FIND_MEMBER));

        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.CANNOT_FIND_BOARD));

        Article article = new Article(
                writer,
                board,
                dto.getTitle(),
                dto.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        articleRepository.save(article);
        return new ArticleResponseDto(article);
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long id, ArticleRequestDto dto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ErrorCode.CANNOT_FIND_ARTICLE));

        Member writer = memberRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new InvalidArticleReferenceException(ErrorCode.MEMBER_DOESNT_EXISTS));

        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new InvalidArticleReferenceException(ErrorCode.BOARD_DOESNT_EXISTS));

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setModifiedDate(LocalDateTime.now());
        article.setWriter(writer);
        article.setBoard(board);

        articleRepository.save(article);
        return new ArticleResponseDto(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException(ErrorCode.CANNOT_FIND_ARTICLE);
        }
        articleRepository.deleteById(id);
    }
}
