package com.shinkwang.devjob.controller.docs;

import com.shinkwang.devjob.domain.Role;
import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.MemberResponse;
import com.shinkwang.devjob.dto.RoleChangeRequest;
import com.shinkwang.devjob.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자")
public interface AdminApiDocs {

    @Operation(summary = "회원 목록 조회", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<List<MemberResponse>> members(Role role);

    @Operation(summary = "회원 권한 변경", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<MemberResponse> changeRole(Long id, RoleChangeRequest req);

    @Operation(summary = "공고 강제 삭제", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Void> deleteJob(Long id);
}
