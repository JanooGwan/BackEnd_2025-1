package com.example.bcsd.global;

import com.example.bcsd.model.Article;
import com.example.bcsd.model.Board;
import com.example.bcsd.model.Member;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public DataInitializer(BoardRepository boardRepository,
                           MemberRepository memberRepository,
                           ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public void run(String... args) {

        Board board1 = boardRepository.save(new Board("자유게시판"));
        Board board2 = boardRepository.save(new Board("공지사항"));
        Board board3 = boardRepository.save(new Board(("공부게시판")));

        Member member1 = memberRepository.save(new Member("이관우", "janoogwan39@gmail.com", "qwe123"));
        Member member2 = memberRepository.save(new Member("관우", "janoogwan39@koreatech.ac.kr", "1234567890"));
        Member member3 = memberRepository.save(new Member("자누", "janoogwan39@naver.com", "asdf1234"));

        articleRepository.save(new Article(member1.getId(), board1.getId(), "첫글입니다", "반갑습니다"));
        articleRepository.save(new Article(member2.getId(), board2.getId(), "[필독]국가장학금 신청 안내", "20일부터 시작합니다"));
        articleRepository.save(new Article(member3.getId(), board3.getId(), "네트워크 관련 강의 추천 받습니다", "좋은 답변 미리 감사합니다"));
        articleRepository.save(new Article(member1.getId(), board3.getId(), "프로젝트 같이 할 사람 구합니다", "BackEnd 1명, FrontEnd 1명씩 모집하겠습니다"));
        articleRepository.save(new Article(member2.getId(), board1.getId(), "가입인사", "잘 부탁 드립니다"));
        articleRepository.save(new Article(member3.getId(), board2.getId(), "[생활관]동계방학 기숙사 사전조사 안내", "아우미르 사이트 참고 바랍니다"));
    }
}
