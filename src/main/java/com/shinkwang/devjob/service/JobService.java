package com.shinkwang.devjob.service;

import com.shinkwang.devjob.domain.Company;
import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.dto.JobCreateRequest;
import com.shinkwang.devjob.dto.JobResponse;
import com.shinkwang.devjob.dto.PageResponse;
import com.shinkwang.devjob.exception.BusinessException;
import com.shinkwang.devjob.exception.ErrorCode;
import com.shinkwang.devjob.repository.CompanyRepository;
import com.shinkwang.devjob.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public JobResponse create(JobCreateRequest req) {
        Company company = companyRepository.findById(req.companyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        Job job = new Job();
        job.setCompany(company);
        job.setTitle(req.title());
        job.setDescription(req.description());
        job.setSalary(req.salary());
        job.setStatus(JobStatus.OPEN);

        return JobResponse.from(jobRepository.save(job));
    }

    public Optional<JobResponse> findById(Long id) {
        return jobRepository.findById(id).map(JobResponse::from);
    }

    public PageResponse<JobResponse> findByStatus(JobStatus status, Pageable pageable) {

        return PageResponse.of(
                jobRepository.findByStatus(status, pageable)
                        .map(JobResponse::from)
        );
    }
    @Transactional
    public JobResponse update(Long id, JobUpdateRequest req) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_NOT_FOUND));
        job.update(req.title(), req.description(), req.salary());
        return JobResponse.from(job);
    }

    @Transactional
    public void delete(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.JOB_NOT_FOUND);
        }
        jobRepository.deleteById(id);
    }
}
