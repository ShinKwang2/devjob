package com.shinkwang.devjob.controller.docs;

import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.MemberJoinRequest;
import com.shinkwang.devjob.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원", description = "Member API")
public interface MemberApiDocs {

    @Operation(summary = "회원가입", description = "이메일 중복 체크 후 BCrypt로 비밀번호를 암호화하여 저장한다")
    ResponseEntity<ApiResponse<MemberResponse>> join(MemberJoinRequest req);
}
