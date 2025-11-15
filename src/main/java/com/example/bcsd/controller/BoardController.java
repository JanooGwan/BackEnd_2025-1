package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.request.BoardCreateRequest;
import com.example.bcsd.controller.dto.request.BoardUpdateRequest;
import com.example.bcsd.controller.dto.response.ArticleResponse;
import com.example.bcsd.controller.dto.response.BoardResponse;
import com.example.bcsd.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable("id") Long id) {
        BoardResponse response = boardService.getBoardById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardCreateRequest boardCreateRequestDto) {
        BoardResponse response = boardService.createBoard(boardCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateRequest boardUpdateRequestDto) {
        BoardResponse response = boardService.updateBoard(id, boardUpdateRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardById(@PathVariable Long id) {
        boardService.deleteBoard(id);

        return ResponseEntity.noContent().build();
    }
}
