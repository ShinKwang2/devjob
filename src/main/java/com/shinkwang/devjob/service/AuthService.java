package com.shinkwang.devjob.service;

import com.shinkwang.devjob.domain.Member;
import com.shinkwang.devjob.dto.LoginRequest;
import com.shinkwang.devjob.dto.LoginResponse;
import com.shinkwang.devjob.exception.BusinessException;
import com.shinkwang.devjob.exception.ErrorCode;
import com.shinkwang.devjob.repository.MemberRepository;
import com.shinkwang.devjob.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String DUMMY_PASSWORD_HASH = "$2a$10$zGcm02B9oXP0IVp0GSCG1OOAluSeTUpJtx4fJYFnm8MC87VJ0jPk2";

    public LoginResponse login(LoginRequest req) {
        Member member = memberRepository.findByEmail(req.email())
                .orElse(null);

        String encodedPassword = member != null
                ? member.getPassword()
                : DUMMY_PASSWORD_HASH;

        boolean passwordMatches = passwordEncoder.matches(req.password(), encodedPassword);

        if (member == null || !passwordMatches) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        String accessToken = jwtTokenProvider.createToken(member.getId(), member.getRole());
        return LoginResponse.from(accessToken, member.getId(), member.getEmail(), member.getRole());
    }
}
