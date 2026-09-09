package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.dto.ApiResponse;
import com.shinkwang.devjob.dto.JobCreateRequest;
import com.shinkwang.devjob.dto.JobResponse;
import com.shinkwang.devjob.dto.PageResponse;
import com.shinkwang.devjob.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<ApiResponse<JobResponse>> create(@RequestBody @Valid JobCreateRequest req) {
        JobResponse job = jobService.create(req);
        URI location = URI.create("/api/jobs/" + job.id());
        return ResponseEntity.created(location).body(ApiResponse.ok(job));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJob(@PathVariable Long id) {
        return jobService.findById(id)
                .map(job -> ResponseEntity.ok(ApiResponse.ok(job)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ApiResponse<PageResponse<JobResponse>> getJobs(
            @RequestParam(defaultValue = "OPEN")JobStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.ok(jobService.findByStatus(status, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
