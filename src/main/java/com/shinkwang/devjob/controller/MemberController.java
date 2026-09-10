package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.MemberJoinRequest;
import com.shinkwang.devjob.dto.MemberResponse;
import com.shinkwang.devjob.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<MemberResponse>> join(
            @RequestBody @Valid MemberJoinRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(memberService.join(req)));
    }

}
