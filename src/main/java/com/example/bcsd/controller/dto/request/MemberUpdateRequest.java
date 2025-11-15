package com.example.bcsd.controller.dto.request;

public record MemberUpdateRequest(
        String name,
        String email,
        String password
) {

}
