package com.example.bcsd.service;

import com.example.bcsd.controller.dto.request.LoginRequest;
import com.example.bcsd.controller.dto.request.MemberCreateRequest;
import com.example.bcsd.controller.dto.response.MemberResponse;
import com.example.bcsd.controller.dto.response.TokenResponse;
import com.example.bcsd.entity.Member;
import com.example.bcsd.global.exception.CustomException;
import com.example.bcsd.global.exception.ErrorCode;
import com.example.bcsd.global.security.JwtProvider;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final long ACCESS_TOKEN_EXPIRATION_SECONDS = 3600L;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public MemberResponse signup(MemberCreateRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        Member savedMember = memberRepository.save(member);
        return MemberResponse.from(savedMember);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(member.getId(), member.getEmail());

        return TokenResponse.of(accessToken, refreshToken, ACCESS_TOKEN_EXPIRATION_SECONDS);
    }

    @Transactional(readOnly = true)
    public TokenResponse refresh(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Long memberId = jwtProvider.getMemberIdFromToken(refreshToken);
        String email = jwtProvider.getEmailFromToken(refreshToken);

        memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        String newAccessToken = jwtProvider.createAccessToken(memberId, email);

        return TokenResponse.of(newAccessToken, refreshToken, ACCESS_TOKEN_EXPIRATION_SECONDS);
    }
}
