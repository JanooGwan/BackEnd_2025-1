package com.example.bcsd.controller.dto.request;

import com.example.bcsd.entity.Member;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest (
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
    public Member toEntity() { return new Member(name, email, password); }
}
