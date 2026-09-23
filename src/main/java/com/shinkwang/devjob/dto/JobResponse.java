package com.shinkwang.devjob.dto;

import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;

import java.time.LocalDate;

public record JobResponse(
        Long id,
        Long companyId,
        String companyName,
        String title,
        String description,
        Integer salary,
        JobStatus status,
        LocalDate deadline
) {
    public static JobResponse from(Job job) {
        return new JobResponse(
                job.getId(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getTitle(),
                job.getDescription(),
                job.getSalary(),
                job.getStatus(),
                job.getDeadline()
        );
    }
}
