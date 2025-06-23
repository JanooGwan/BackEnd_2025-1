package com.example.bcsd.service;

import com.example.bcsd.domain.Board;
import com.example.bcsd.dto.BoardRequestDto;
import com.example.bcsd.dto.BoardResponseDto;
import com.example.bcsd.exception.*;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public BoardService(BoardRepository boardRepository, ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.articleRepository = articleRepository;
    }

    public List<BoardResponseDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardResponseDto::new)
                .toList();
    }


    public BoardResponseDto getBoardById(Long id) {
        return boardRepository.findById(id)
                .map(BoardResponseDto::new)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.CANNOT_FIND_BOARD));
    }

    public String getBoardNameById(Long id) {
        return boardRepository.findNameById(id)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.CANNOT_FIND_BOARD));
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto dto) {
        Board board = new Board(dto.getName());
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.CANNOT_FIND_BOARD));

        if (articleRepository.existsByBoard_Id(id)) {
            throw new BoardDeletionNotAllowedException(ErrorCode.BOARD_HAS_ARTICLES);
        }
    }
}