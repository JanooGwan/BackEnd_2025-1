package com.example.bcsd.service;

import com.example.bcsd.domain.Member;
import com.example.bcsd.dto.MemberRegisterRequestDto;
import com.example.bcsd.dto.MemberRequestDto;
import com.example.bcsd.dto.MemberResponseDto;
import com.example.bcsd.dto.MemberUpdateRequestDto;
import com.example.bcsd.exception.EmailAlreadyExistsException;
import com.example.bcsd.exception.ErrorCode;
import com.example.bcsd.exception.MemberDeletionNotAllowedException;
import com.example.bcsd.exception.MemberNotFoundException;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public MemberResponseDto getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponseDto::from)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.CANNOT_FIND_MEMBER));
    }

    @Transactional
    public Member registerMember(MemberRegisterRequestDto dto, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = dto.toEntity(encodedPassword);
        return memberRepository.save(member);
    }



    @Transactional
    public MemberResponseDto updateMember(MemberUpdateRequestDto dto, Long id, PasswordEncoder passwordEncoder) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.CANNOT_FIND_MEMBER));

        Optional<Member> memberEmailCheck = memberRepository.findByEmail(dto.getEmail());
        if (memberEmailCheck.isPresent() && !memberEmailCheck.get().getId().equals(id)) {
            throw new EmailAlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        member.setEmail(dto.getEmail());
        member.setName(dto.getName());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));

        return MemberResponseDto.from(member);
    }


    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.CANNOT_FIND_MEMBER));

        if (articleRepository.existsByWriter_Id(id)) {
            throw new MemberDeletionNotAllowedException(ErrorCode.MEMBER_HAS_ARTICLES);
        }

        memberRepository.delete(member);
    }

}
