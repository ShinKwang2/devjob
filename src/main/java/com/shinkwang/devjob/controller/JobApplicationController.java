package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.controller.docs.JobApplicationApiDocs;
import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.JobApplicationResponse;
import com.shinkwang.devjob.security.CustomUserDetails;
import com.shinkwang.devjob.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class JobApplicationController implements JobApplicationApiDocs {

    private final JobApplicationService jobApplicationService;

    @Override
    @PostMapping("/api/jobs/{jobId}/apply")
    public ResponseEntity<ApiResponse<JobApplicationResponse>> apply(
            @PathVariable Long jobId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        JobApplicationResponse result = jobApplicationService.apply(user.getId(), jobId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(result));
    }

    @Override
    @GetMapping("/api/members/me/applications")
    public ApiResponse<List<JobApplicationResponse>> myJobApplications(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.ok(jobApplicationService.findMyJobApplications(user.getId()));
    }
}
