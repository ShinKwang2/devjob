package com.shinkwang.devjob.health;

import com.shinkwang.devjob.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobHealthIndicator implements HealthIndicator {

    private final JobRepository jobRepository;

    @Override
    public @Nullable Health health() {
        try {
            long count = jobRepository.count();
            return Health.up().withDetail("totalJobs", count).build();
        } catch (Exception e) {
            return Health.down().withException(e).build();
        }
    }
}
