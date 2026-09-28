package com.shinkwang.devjob.scheduler;

import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JobScheduler {

    private final JobRepository jobRepository;

    @Scheduled(cron = "10 0 0 * * *")
    @Transactional
    public void closeExpiredJobs() {
        List<Job> expiredJobs = jobRepository.findByStatusAndDeadlineBefore(JobStatus.OPEN, LocalDate.now());

        expiredJobs.forEach(job -> job.changeStatus(JobStatus.CLOSED));
        log.info("[Close Expired Jobs Scheduler] 마감일 경과 공고 {}건 자동 마감 처리 완료", expiredJobs.size());
    }
}
