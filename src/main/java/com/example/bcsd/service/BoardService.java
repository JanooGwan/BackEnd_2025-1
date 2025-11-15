package com.example.bcsd.service;

import com.example.bcsd.controller.dto.request.BoardCreateRequest;
import com.example.bcsd.controller.dto.request.BoardUpdateRequest;
import com.example.bcsd.controller.dto.response.BoardResponse;
import com.example.bcsd.model.Board;
import com.example.bcsd.repository.BoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResponse::from)
                .toList();
    }

    public BoardResponse getBoardById(Long id) {
        return boardRepository.findById(id)
                .map(BoardResponse::from)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "ID에 해당하는 게시판이 존재하지 않습니다."));
    }

    public BoardResponse createBoard(BoardCreateRequest requestDto) {
        Board board = boardRepository.save(requestDto.toEntity());
        return BoardResponse.from(board);
    }

    public BoardResponse updateBoard(Long id, BoardUpdateRequest requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "ID에 해당하는 게시판이 존재하지 않습니다."));

        board.update(requestDto.name());
        Board updatedBoard = boardRepository.update(id, board);
        return BoardResponse.from(updatedBoard);
    }

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "ID에 해당하는 게시판이 존재하지 않습니다."));

        boardRepository.deleteById(id);
    }
}
