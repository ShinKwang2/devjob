package com.shinkwang.devjob.dto;

import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;

public record JobResponse(
        Long id,
        Long companyId,
        String title,
        String description,
        Integer salary,
        JobStatus status
) {
    public static JobResponse from(Job job) {
        return new JobResponse(
                job.getId(),
                job.getCompanyId(),
                job.getTitle(),
                job.getDescription(),
                job.getSalary(),
                job.getStatus()
        );
    }
}
