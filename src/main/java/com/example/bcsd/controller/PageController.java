package com.example.bcsd.controller;

import com.example.bcsd.service.ArticleService;
import com.example.bcsd.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final ArticleService articleService;
    private final BoardService boardService;

    public PageController(ArticleService articleService, BoardService boardService) {
        this.articleService = articleService;
        this.boardService = boardService;
    }

    @GetMapping("/introduce")
    public String introduce(
            @RequestParam(name = "name", defaultValue = "이관우")
            String name,
            Model model
    ) {
        model.addAttribute("name", name);
        return "introduction";
    }

    @GetMapping("/posts")
    public String posts(@RequestParam(required = false) Long boardId, Model model) {
        if (boardId == null) {
            model.addAttribute("boards", articleService.getBoardsWithArticles());
            return "posts";
        }

        model.addAttribute("boardName", boardService.getBoardById(boardId).name());
        model.addAttribute("articles", articleService.getArticlesByBoardWithWriter(boardId));
        return "posts";
    }

}
