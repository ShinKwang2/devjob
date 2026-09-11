package com.shinkwang.devjob.controller.docs;

import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.LoginRequest;
import com.shinkwang.devjob.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "인증")
public interface AuthApiDocs {

    @Operation(summary = "로그인", description = "이메일/비밀번호로 로그인하고 JWT를 발급받는다")
    ApiResponse<LoginResponse> login(LoginRequest req);
}
