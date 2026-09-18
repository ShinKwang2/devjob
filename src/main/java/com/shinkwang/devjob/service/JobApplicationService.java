package com.shinkwang.devjob.service;

import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobApplication;
import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.domain.Member;
import com.shinkwang.devjob.dto.JobApplicationResponse;
import com.shinkwang.devjob.exception.BusinessException;
import com.shinkwang.devjob.exception.ErrorCode;
import com.shinkwang.devjob.repository.JobApplicationRepository;
import com.shinkwang.devjob.repository.JobRepository;
import com.shinkwang.devjob.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public JobApplicationResponse apply(Long memberId, Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_NOT_FOUND));

        if (job.getStatus() == JobStatus.CLOSED) {
            throw new BusinessException(ErrorCode.JOB_CLOSED);
        }
        if (jobApplicationRepository.existsByMemberIdAndJobId(memberId, jobId)) {
            throw new BusinessException(ErrorCode.ALREADY_APPLIED);
        }

        Member member = memberRepository.getReferenceById(memberId);

        JobApplication saved = jobApplicationRepository.save(JobApplication.builder()
                .member(member)
                .job(job)
                .build()
        );

        return JobApplicationResponse.from(saved);
    }

    public List<JobApplicationResponse> findMyJobApplications(Long memberId) {
        return jobApplicationRepository.findByMemberIdWithJob(memberId).stream()
                .map(JobApplicationResponse::from)
                .toList();
    }
}
