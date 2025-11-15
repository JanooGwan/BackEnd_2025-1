package com.example.bcsd.service;

import com.example.bcsd.controller.dto.request.MemberCreateRequest;
import com.example.bcsd.controller.dto.request.MemberUpdateRequest;
import com.example.bcsd.controller.dto.response.MemberResponse;
import com.example.bcsd.model.Member;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 회원이 존재하지 않습니다."));
    }

    public MemberResponse createMember(MemberCreateRequest requestDto) {
        Member member = memberRepository.save(requestDto.toEntity());
        return MemberResponse.from(member);
    }

    public MemberResponse updateMember(Long id, MemberUpdateRequest requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 회원이 존재하지 않습니다.")
                );

        member.update(requestDto.name(), requestDto.email(), requestDto.password());
        Member updatedMember = memberRepository.update(id, member);

        return MemberResponse.from(updatedMember);
    }


    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "ID에 해당하는 회원이 존재하지 않습니다.")
                );

        memberRepository.deleteById(id);
    }
}
