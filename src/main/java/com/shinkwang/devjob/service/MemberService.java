package com.shinkwang.devjob.service;

import com.shinkwang.devjob.domain.Member;
import com.shinkwang.devjob.domain.Role;
import com.shinkwang.devjob.dto.MemberJoinRequest;
import com.shinkwang.devjob.dto.MemberResponse;
import com.shinkwang.devjob.exception.BusinessException;
import com.shinkwang.devjob.exception.ErrorCode;
import com.shinkwang.devjob.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse join(MemberJoinRequest req) {
        if (memberRepository.existsByEmail(req.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
        Member member = Member.builder()
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.USER)
                .build();
        return MemberResponse.from(memberRepository.save(member));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public List<Member> findByRole(Role role) {
        return memberRepository.findByRoleOrderByCreatedAtDesc(role);
    }
}
