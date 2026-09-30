package com.shinkwang.devjob.controller;

import com.shinkwang.devjob.controller.docs.JobApiDocs;
import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.dto.*;
import com.shinkwang.devjob.security.CustomUserDetails;
import com.shinkwang.devjob.service.JobService;
import com.shinkwang.devjob.service.result.JobDetailResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Duration;

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
public class JobController implements JobApiDocs {

    private static final CacheControl JOB_DETAIL_CACHE_CONTROL = CacheControl.maxAge(Duration.ofSeconds(60))
            .cachePrivate()
            .mustRevalidate();

    private final JobService jobService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<JobResponse>> create(
            @RequestBody @Valid JobCreateRequest req,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        JobResponse job = jobService.create(req, user.getId());
        URI location = URI.create("/api/jobs/" + job.id());
        return ResponseEntity.created(location).body(ApiResponse.ok(job));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJob(
            @PathVariable Long id,
            WebRequest request
    ) {
        JobDetailResult detail = jobService.findDetail(id);

        // HTTP ETag 문법상 큰따옴표가 필요하다
        String etag = "\"" + detail.validator() + "\"";

        if (request.checkNotModified(etag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(etag)
                    .cacheControl(JOB_DETAIL_CACHE_CONTROL)
                    .build();
        }

        return ResponseEntity.ok()
                .eTag(etag)
                .cacheControl(JOB_DETAIL_CACHE_CONTROL)
                .body(ApiResponse.ok(detail.response()));
    }

    @Override
    @GetMapping
    public ApiResponse<PageResponse<JobResponse>> getJobs(
            @RequestParam(defaultValue = "OPEN")JobStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.ok(jobService.findByStatus(status, pageable));
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<JobResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid JobUpdateRequest req,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.ok(jobService.update(id, req, user.getId(), isAdmin(user)));
    }

    @Override
    @GetMapping("/search")
    public ApiResponse<PageResponse<JobResponse>> search(
            @ModelAttribute JobSearchRequest req,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.ok(jobService.search(req, pageable));
    }


    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        jobService.delete(id, user.getId(), isAdmin(user));
        return ResponseEntity.noContent().build();
    }

    private boolean isAdmin(CustomUserDetails user){
        return user.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }
}
