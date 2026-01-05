package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.request.LoginRequest;
import com.example.bcsd.controller.dto.request.MemberCreateRequest;
import com.example.bcsd.controller.dto.response.MemberResponse;
import com.example.bcsd.controller.dto.response.TokenResponse;
import com.example.bcsd.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@Valid @RequestBody MemberCreateRequest request) {
        MemberResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
        TokenResponse response = authService.refresh(refreshToken);
        return ResponseEntity.ok(response);
    }
}
