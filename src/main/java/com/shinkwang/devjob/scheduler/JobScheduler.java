package com.shinkwang.devjob.scheduler;

import com.shinkwang.devjob.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@RequiredArgsConstructor
@Slf4j
@Component
public class JobScheduler {

    private static final ZoneId BUSINESS_ZONE = ZoneId.of("Asia/Seoul");

    private final JobService jobService;

    @Scheduled(cron = "10 0 0 * * *", zone = "Asia/Seoul")
    public void closeExpiredJobs() {
        int closedCount = jobService.closeExpiredJobs(LocalDate.now(BUSINESS_ZONE));

        log.info("[Close Expired Jobs Scheduler] 마감일 경과 공고 {}건 자동 마감 처리 완료", closedCount);
    }
}
