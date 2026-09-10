package com.shinkwang.devjob.dto;

import com.shinkwang.devjob.domain.Member;
import com.shinkwang.devjob.domain.Role;

public record MemberResponse(
        Long id,
        String email,
        Role role
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getRole());
    }
}
