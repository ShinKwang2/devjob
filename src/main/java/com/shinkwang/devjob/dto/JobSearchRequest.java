package com.shinkwang.devjob.dto;

import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.repository.JobSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public record JobSearchRequest(
        String keyword, // 제목 또는 회사명
        String location, // 지역
        JobStatus status, // 공고 상태
        Integer minSalary, // 최소 연봉(이상)
        Integer maxSalary, // 최대 연봉(이하)
        LocalDate deadlineBefore // 이 날짜 이전 마감
) {

    public Specification<Job> toSpecification() {
        return Specification.where(JobSpecification.withKeyword(keyword))
                .and(JobSpecification.withLocation(location))
                .and(JobSpecification.withStatus(status))
                .and(JobSpecification.withSalaryGreaterOrEqual(minSalary))
                .and(JobSpecification.withSalaryLessOrEqual(maxSalary))
                .and(JobSpecification.withDeadlineBefore(deadlineBefore));
    }
}
