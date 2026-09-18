package com.shinkwang.devjob.controller.docs;

import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.JobApplicationResponse;
import com.shinkwang.devjob.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Tag(name = "지원")
public interface JobApplicationApiDocs {

    @Operation(summary = "공고 지원", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<ApiResponse<JobApplicationResponse>> apply(Long jobId, CustomUserDetails user);

    @Operation(summary = "내 지원 내역", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<List<JobApplicationResponse>> myJobApplications(CustomUserDetails user);
}
