package com.example.bcsd.controller;

import com.example.bcsd.domain.Member;
import com.example.bcsd.dto.*;
import com.example.bcsd.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public MemberResponseDto getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    // 로그인 & 로그아웃 구현하기 전
    /*
    @PostMapping
    public MemberResponseDto createMember(@RequestBody @Valid MemberRequestDto dto) {
        return memberService.createMember(dto);
    }
     */

    @PostMapping("/register")
    public MemberRegisterResponseDto registerMember(
            @Valid MemberRegisterRequestDto dto) {

        Member saved = memberService.registerMember(dto, passwordEncoder);
        return MemberRegisterResponseDto.from(saved);
    }



    @PutMapping("/{id}")
    public MemberResponseDto updateMember(
            @PathVariable Long id,
            @RequestBody @Valid MemberUpdateRequestDto dto) {

        return memberService.updateMember(dto, id, passwordEncoder);
    }


    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}