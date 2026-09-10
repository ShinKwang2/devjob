package com.shinkwang.devjob.repository;

import com.shinkwang.devjob.domain.Member;
import com.shinkwang.devjob.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Member> findByRoleOrderByCreatedAtDesc(Role role);
}
