package com.shinkwang.devjob.config;

import com.shinkwang.devjob.domain.Member;
import com.shinkwang.devjob.domain.Role;
import com.shinkwang.devjob.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AdminAccountInitializer {

    private static final String ADMIN_EMAIL = "admin@devjob.com";
    private static final String ADMIN_PASSWORD = "admin";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initAdminAccount() {
        if (memberRepository.existsByEmail(ADMIN_EMAIL)) {
            return;
        }

        Member admin = Member.builder()
                .email(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(Role.ADMIN)
                .build();
        memberRepository.save(admin);
        log.info("관리자 계정이 생성되었습니다. {}", ADMIN_EMAIL);
    }
}
