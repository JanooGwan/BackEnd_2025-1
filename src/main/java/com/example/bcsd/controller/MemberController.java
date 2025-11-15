package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.request.MemberCreateRequest;
import com.example.bcsd.controller.dto.request.MemberUpdateRequest;
import com.example.bcsd.controller.dto.response.MemberResponse;
import com.example.bcsd.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("id") Long id) {
        MemberResponse response = memberService.getMemberById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberCreateRequest memberCreateRequestDto) {
        MemberResponse response = memberService.createMember(memberCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest memberUpdateRequestDto) {
        MemberResponse response = memberService.updateMember(id, memberUpdateRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);

        return ResponseEntity.noContent().build();
    }
}
