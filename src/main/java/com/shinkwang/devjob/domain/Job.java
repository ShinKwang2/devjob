package com.shinkwang.devjob.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "job")
@Entity
public class Job {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobStatus status = JobStatus.OPEN;

    @Column(name = "registered_by", nullable = false)
    private Long registeredBy;

    // 공고 마감일. null이면 상시 채용으로 간주
    private LocalDate deadline;

    @Version
    @Column(nullable = false)
    private Long version;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Job create(
            Company company,
            String title,
            String description,
            Integer salary,
            LocalDate deadline,
            Long registeredBy
    ) {
        Job job = new Job();
        job.company = company;
        job.title = title;
        job.description = description;
        job.salary = salary;
        job.deadline = deadline;
        job.registeredBy = registeredBy;
        job.status = JobStatus.OPEN;
        return job;
    }

    public void update(String title, String description, Integer salary, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.deadline = deadline;
    }

    public void changeStatus(JobStatus status) {
        this.status = status;
    }
}
