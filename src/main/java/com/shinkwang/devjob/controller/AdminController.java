package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.controller.docs.AdminApiDocs;
import com.shinkwang.devjob.domain.Role;
import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.MemberResponse;
import com.shinkwang.devjob.dto.RoleChangeRequest;
import com.shinkwang.devjob.service.JobService;
import com.shinkwang.devjob.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController implements AdminApiDocs {

    private final MemberService memberService;
    private final JobService jobService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/members")
    public ApiResponse<List<MemberResponse>> members(@RequestParam(required = false) Role role) {
        return ApiResponse.ok(memberService.findAll(role));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/members/{id}/role")
    public ApiResponse<MemberResponse> changeRole(
            @PathVariable Long id,
            @RequestBody @Valid RoleChangeRequest req
    ) {
        return ApiResponse.ok(memberService.changeRole(id, req.role()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
