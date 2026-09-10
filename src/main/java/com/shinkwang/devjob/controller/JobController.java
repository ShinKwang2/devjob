package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.dto.*;
import com.shinkwang.devjob.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * 채용 공고 API.
 *
 * - POST   /api/jobs       → 201 Created + Location 헤더
 * - GET    /api/jobs/{id}  → 200 OK / 404 Not Found
 * - GET    /api/jobs       → status + Pageable(page/size/sort) 기반 페이징 목록
 * - PUT    /api/jobs/{id}  → 200 OK (전체 교체)
 * - GET    /api/jobs/search → 제목/회사명/지역 통합 검색
 * - DELETE /api/jobs/{id}  → 204 No Content
 */
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
    public ApiResponse<JobResponse> getJob(@PathVariable Long id) {
        return ApiResponse.ok(jobService.findById(id));
    }

    @GetMapping
    public ApiResponse<PageResponse<JobResponse>> getJobs(
            @RequestParam(defaultValue = "OPEN")JobStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.ok(jobService.findByStatus(status, pageable));
    }

    @PutMapping("/{id}")
    public ApiResponse<JobResponse> update(@PathVariable Long id, @RequestBody @Valid JobUpdateRequest req) {
        return ApiResponse.ok(jobService.update(id, req));
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<JobResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.ok(jobService.search(keyword, location, pageable));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
