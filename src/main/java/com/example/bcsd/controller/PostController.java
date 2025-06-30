package com.example.bcsd.controller;

import com.example.bcsd.domain.Member;
import com.example.bcsd.dto.ArticleResponseDto;
import com.example.bcsd.repository.MemberRepository;
import com.example.bcsd.service.ArticleService;
import com.example.bcsd.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
    private final ArticleService articleService;
    private final BoardService boardService;
    private final MemberRepository memberRepository;

    public PostController(ArticleService articleService,
                          BoardService boardService,
                          MemberRepository memberRepository) {
        this.articleService = articleService;
        this.boardService = boardService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/posts")
    public String getPosts(Model model, @AuthenticationPrincipal User user) {
        String email = user.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        model.addAttribute("name", member.getName());
        model.addAttribute("articles", articleService.getAllArticles());  // 반드시 ArticleResponseDto 리스트
        return "postView";
    }


    @GetMapping(value = "/posts", params = "boardId")
    public String getPostViewByBoardId(@RequestParam Long boardId,
                                       Model model,
                                       @AuthenticationPrincipal User user) {

        String email = user.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String boardName = boardService.getBoardNameById(boardId);
        List<ArticleResponseDto> articles = articleService.getArticlesByBoardId(boardId);

        model.addAttribute("name", member.getName());
        model.addAttribute("boardName", boardName);
        model.addAttribute("articles", articleService.getAllArticles());

        return "postViewByBoard";
    }
}
