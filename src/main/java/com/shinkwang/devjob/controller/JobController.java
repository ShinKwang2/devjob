package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.JobCreateRequest;
import com.shinkwang.devjob.dto.JobResponse;
import com.shinkwang.devjob.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ApiResponse<JobResponse> create(@RequestBody @Valid JobCreateRequest req) {
        return ApiResponse.ok(jobService.create(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<JobResponse> getJob(@PathVariable Long id) {
        return ApiResponse.ok(jobService.findById(id).orElse(null));
    }

    @GetMapping
    public ApiResponse<Page<JobResponse>> getJobs(
            @RequestParam(defaultValue = "OPEN") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(jobService.findByStatus(status, page, size));
    }
}
