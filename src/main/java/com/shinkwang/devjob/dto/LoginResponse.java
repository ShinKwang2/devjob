package com.shinkwang.devjob.dto;

import com.shinkwang.devjob.domain.Role;
import com.shinkwang.devjob.security.JwtConstants;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long memberId,
        String email,
        Role role
) {

    public static LoginResponse from(String accessToken, Long memberId, String email, Role role) {
        return new LoginResponse(
                accessToken,
                JwtConstants.TOKEN_TYPE,
                memberId,
                email,
                role
        );
    }
}
