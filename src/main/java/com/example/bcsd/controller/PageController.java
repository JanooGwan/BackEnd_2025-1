package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.response.ArticleResponse;
import com.example.bcsd.controller.dto.response.BoardResponse;
import com.example.bcsd.service.ArticleService;
import com.example.bcsd.service.BoardService;
import com.example.bcsd.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final MemberService memberService;

    public PageController(ArticleService articleService, BoardService boardService, MemberService memberService) {
        this.articleService = articleService;
        this.boardService = boardService;
        this.memberService = memberService;
    }

    @GetMapping("/introduce")
    public String introduce(@RequestParam(name = "name", defaultValue = "이관우") String name, Model model) {
        model.addAttribute("name", name);
        return "introduction";
    }

    @GetMapping("/posts")
    public String posts(@RequestParam(name = "boardId", required = false, defaultValue = "1") Long boardId,
                        Model model) {

        BoardResponse board = boardService.getBoardById(boardId);
        model.addAttribute("boardName", board.name());

        List<ArticleResponse> articles = articleService.getArticlesByBoard(boardId);
        model.addAttribute("articles", articles);

        return "posts";
    }
}
