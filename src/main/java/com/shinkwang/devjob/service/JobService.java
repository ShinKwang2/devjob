package com.shinkwang.devjob.service;

import com.shinkwang.devjob.domain.Company;
import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.dto.*;
import com.shinkwang.devjob.exception.BusinessException;
import com.shinkwang.devjob.exception.ErrorCode;
import com.shinkwang.devjob.repository.CompanyRepository;
import com.shinkwang.devjob.repository.JobRepository;
import com.shinkwang.devjob.util.SortValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JobService {

    private static final Set<String> ALLOWED_SORT_PROPERTIES =
            Set.of("id", "title", "salary", "status", "deadline", "createdAt", "updatedAt");

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public JobResponse create(JobCreateRequest req, Long registeredBy) {
        Company company = companyRepository.findById(req.companyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        Job job = new Job();
        job.setCompany(company);
        job.setTitle(req.title());
        job.setDescription(req.description());
        job.setSalary(req.salary());
        job.setStatus(JobStatus.OPEN);
        job.setDeadline(req.deadline());
        job.setRegisteredBy(registeredBy);

        return JobResponse.from(jobRepository.save(job));
    }

    public JobResponse findById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_NOT_FOUND));
        return JobResponse.from(job);
    }

    public PageResponse<JobResponse> findByStatus(JobStatus status, Pageable pageable) {

        return PageResponse.of(
                jobRepository.findByStatusWithCompany(status, pageable)
                        .map(JobResponse::from)
        );
    }
    @Transactional
    public JobResponse update(Long id, JobUpdateRequest req, Long requesterId, boolean isAdmin) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_NOT_FOUND));
        checkOwner(job, requesterId, isAdmin);

        job.update(req.title(), req.description(), req.salary());
        return JobResponse.from(job);
    }

    public PageResponse<JobResponse> search(JobSearchRequest req, Pageable pageable) {
        SortValidator.validate(pageable.getSort(), ALLOWED_SORT_PROPERTIES);

        return PageResponse.of(jobRepository.findAll(req.toSpecification(), pageable)
                .map(JobResponse::from)
        );
    }

    @Transactional
    public void delete(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.JOB_NOT_FOUND);
        }
        jobRepository.deleteById(id);
    }

    @Transactional
    public void delete(Long id, Long requesterId, boolean isAdmin) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_NOT_FOUND));
        checkOwner(job, requesterId, isAdmin);

        jobRepository.deleteById(id);
    }

    private void checkOwner(Job job, Long requesterId, boolean isAdmin) {
        if (!isAdmin && !job.getRegisteredBy().equals(requesterId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
