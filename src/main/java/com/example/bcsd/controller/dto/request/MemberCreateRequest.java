package com.example.bcsd.controller.dto.request;

import com.example.bcsd.model.Member;

public record MemberCreateRequest (
        String name,
        String email,
        String password
) {
    public Member toEntity() { return new Member(name, email, password); }
}
