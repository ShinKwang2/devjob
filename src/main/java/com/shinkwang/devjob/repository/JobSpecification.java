package com.shinkwang.devjob.repository;

import com.shinkwang.devjob.domain.Company;
import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * 검색 조건이 없으면 조건을 생략하고, 있으면 추가하는 동적 쿼리 조립.
 * Specification이 null을 반환하면 해당 조건은 자동으로 건너 뛴다
 */
public class JobSpecification {

    private JobSpecification(){}

    public static Specification<Job> withKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            Join<Job, Company> company = root.join("company");
            return cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(company.get("name")), "%" + keyword.toLowerCase() + "%")
            );
        };
    }

    public static Specification<Job> withLocation(String location) {
        return (root, query, cb) -> {
            if (location == null || location.isBlank()) {
                return null;
            }
            return cb.equal(root.join("company").get("location"), location);
        };
    }

    /**
     * 공고 상태(OPEN/CLOSED) 필터. status가 없으면 전체 상태를 대상으로 한다.
     */
    public static Specification<Job> withStatus(JobStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }

    /**
     * 최소 연봉(이상) 필터
     */
    public static Specification<Job> withSalaryGreaterOrEqual(Integer minSalary) {
        return (root, query, cb) -> {
            if (minSalary == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
        };
    }

    /**
     * 최대 연봉(이하) 필터
     */
    public static Specification<Job> withSalaryLessOrEqual(Integer maxSalary) {
        return (root, query, cb) -> {
            if (maxSalary == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get("salary"), maxSalary);
        };
    }

    /**
     * 마감 임박 공고 필터 - deadline이 주어진 날짜 이전(포함)인 공고만 조회
     * deadline이 null(상시 채용)인 공고는 결과에서 제외
     */
    public static Specification<Job> withDeadlineBefore(LocalDate date) {
        return (root, query, cb) -> {
            if (date == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get("deadline"), date);
        };
    }
}
