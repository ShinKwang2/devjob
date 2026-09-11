package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.controller.docs.MemberApiDocs;
import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.MemberJoinRequest;
import com.shinkwang.devjob.dto.MemberResponse;
import com.shinkwang.devjob.security.CustomUserDetails;
import com.shinkwang.devjob.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController implements MemberApiDocs {

    private final MemberService memberService;

    @Override
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<MemberResponse>> join(
            @RequestBody @Valid MemberJoinRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(memberService.join(req)));
    }

    @GetMapping("/me")
    public ApiResponse<MemberResponse> me(@AuthenticationPrincipal CustomUserDetails user) {
        return ApiResponse.ok(MemberResponse.from(memberService.findById(user.getId())));
    }

}
