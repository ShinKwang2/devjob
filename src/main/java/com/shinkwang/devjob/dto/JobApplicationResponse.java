package com.shinkwang.devjob.dto;

import com.shinkwang.devjob.domain.JobApplication;
import com.shinkwang.devjob.domain.JobApplicationStatus;

import java.time.LocalDateTime;

public record JobApplicationResponse(
        Long id,
        Long jobId,
        String jobTitle,
        String companyName,
        JobApplicationStatus status,
        LocalDateTime createdAt
) {
    public static JobApplicationResponse from(JobApplication apply) {
        return new JobApplicationResponse(
                apply.getId(),
                apply.getJob().getId(),
                apply.getJob().getTitle(),
                apply.getJob().getCompany().getName(),
                apply.getStatus(),
                apply.getCreateAt()
        );
    }
}
