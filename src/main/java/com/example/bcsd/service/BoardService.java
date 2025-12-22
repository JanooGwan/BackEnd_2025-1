package com.example.bcsd.service;

import com.example.bcsd.controller.dto.request.BoardCreateRequest;
import com.example.bcsd.controller.dto.request.BoardUpdateRequest;
import com.example.bcsd.controller.dto.response.BoardResponse;
import com.example.bcsd.global.exception.CustomException;
import com.example.bcsd.global.exception.ErrorCode;
import com.example.bcsd.entity.beforejpa.Board;
import com.example.bcsd.repository.BoardRepositoryJdbc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepositoryJdbc boardRepository;

    public BoardService(BoardRepositoryJdbc boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoardById(Long id) {
        return boardRepository.findById(id)
                .map(BoardResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest requestDto) {
        Board board = boardRepository.save(requestDto.toEntity());
        return BoardResponse.from(board);
    }

    @Transactional
    public BoardResponse updateBoard(Long id, BoardUpdateRequest requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        board.update(requestDto.name());
        Board updatedBoard = boardRepository.update(id, board);
        return BoardResponse.from(updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        int articleCount = boardRepository.countArticlesByBoard(id);
        if (articleCount > 0) {
            throw new CustomException(ErrorCode.CANNOT_DELETE_BOARD_WITH_ARTICLES);
        }

        boardRepository.deleteById(id);
    }

}
