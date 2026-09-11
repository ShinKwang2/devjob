package com.shinkwang.devjob.controller.docs;

import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "채용 공고", description = "Job API")
public interface JobApiDocs {

    @Operation(summary = "채용 공고 등록", description = "새 채용 공고를 등록한다")
    ResponseEntity<ApiResponse<JobResponse>> create(JobCreateRequest req);

    @Operation(summary = "채용 공고 단건 조회")
    ApiResponse<JobResponse> getJob(@Parameter(description = "채용공고 ID", example = "1") Long id);

    @Operation(summary = "채용 공고 목록 조회", description = "상태(status)별 페이징 목록을 조회한다.")
    ApiResponse<PageResponse<JobResponse>> getJobs(
            JobStatus status,
            Pageable pageable
    );

    @Operation(summary = "채용 공고 수정", description = "title/description/salary를 전체 교체한다")
    ApiResponse<JobResponse> update(Long id, JobUpdateRequest req);

    @Operation(summary = "채용 공고 검색", description = "제목/회사명/지역으로 OPEN 공고를 통합 검색한다")
    ApiResponse<PageResponse<JobResponse>> search(
            String keyword,
            String location,
            Pageable pageable
    );

    @Operation(summary = "채용 공고 삭제")
    ResponseEntity<Void> delete(Long id);
}