package com.meetmate.meetmatebackend.domain.auth.controller;

import com.meetmate.meetmatebackend.domain.auth.dto.request.SigninRequest;
import com.meetmate.meetmatebackend.domain.auth.dto.request.SignupRequest;
import com.meetmate.meetmatebackend.domain.auth.service.AuthService;
import com.meetmate.meetmatebackend.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ApiResponse<Void> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        authService.signup(request);
        return ApiResponse.success();
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<ApiResponse<Void>> signin(
            @Valid @RequestBody SigninRequest request
    ) {
        String jwt = authService.signin(request);
        return ResponseEntity.ok().header("Authorization", "Bearer" + jwt).body(ApiResponse.success());
    }
}
