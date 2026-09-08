package com.shinkwang.devjob.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Job {

    private Long id;
    private Long companyId;

    private String title;
    private String description;

    private Integer salary;
    private JobStatus status = JobStatus.OPEN;

    public Job(Long id, Long companyId, String title, String description, Integer salary, JobStatus status) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.status = status;
    }
}
