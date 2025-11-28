package com.example.bcsd.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password
) {

}
