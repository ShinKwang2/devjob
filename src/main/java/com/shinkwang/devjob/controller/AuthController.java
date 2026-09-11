package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.controller.docs.AuthApiDocs;
import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.LoginRequest;
import com.shinkwang.devjob.dto.LoginResponse;
import com.shinkwang.devjob.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest req){
        return ApiResponse.ok(authService.login(req));
    }
}
