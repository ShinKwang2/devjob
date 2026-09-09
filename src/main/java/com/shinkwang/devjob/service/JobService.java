package com.shinkwang.devjob.service;

import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;
import com.shinkwang.devjob.dto.JobCreateRequest;
import com.shinkwang.devjob.dto.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class JobService {

    private final Map<Long, Job> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public JobResponse create(JobCreateRequest req) {
        long id = sequence.incrementAndGet();
        Job job = new Job();
        job.setId(id);
        job.setCompanyId(req.companyId());
        job.setTitle(req.title());
        job.setDescription(req.description());
        job.setSalary(req.salary());
        job.setStatus(JobStatus.OPEN);

        store.put(id, job);
        return JobResponse.from(job);
    }

    public Optional<JobResponse> findById(Long id) {
        return Optional.ofNullable(store.get(id)).map(JobResponse::from);
    }

    public Page<JobResponse> findByStatus(String status, int page, int size) {
        JobStatus jobStatus = JobStatus.valueOf(status.toUpperCase());
        List<JobResponse> filtered = new ArrayList<>();
        for (Job job : store.values()) {
            if (job.getStatus() == jobStatus) {
                filtered.add(JobResponse.from(job));
            }
        }

        filtered.sort((a, b) -> Long.compare(a.id(), b.id()));
        int from = Math.min(page * size, filtered.size());
        int to = Math.min(from + size, filtered.size());
        return new PageImpl<>(filtered.subList(from, to), PageRequest.of(page, size), filtered.size());
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
