package com.example.bcsd.service;

import com.example.bcsd.controller.dto.request.MemberCreateRequest;
import com.example.bcsd.controller.dto.request.MemberUpdateRequest;
import com.example.bcsd.controller.dto.response.MemberResponse;
import com.example.bcsd.global.exception.CustomException;
import com.example.bcsd.global.exception.ErrorCode;
import com.example.bcsd.model.Member;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MemberResponse getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest requestDto) {
        Member member = memberRepository.save(requestDto.toEntity());
        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse updateMember(Long id, MemberUpdateRequest requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        member.update(requestDto.name(), requestDto.email(), requestDto.password());
        Member updatedMember = memberRepository.update(id, member);

        return MemberResponse.from(updatedMember);
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        memberRepository.deleteById(id);
    }
}
