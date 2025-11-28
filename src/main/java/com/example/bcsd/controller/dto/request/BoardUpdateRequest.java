package com.example.bcsd.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BoardUpdateRequest(
        @NotBlank
        String name
) {
}
