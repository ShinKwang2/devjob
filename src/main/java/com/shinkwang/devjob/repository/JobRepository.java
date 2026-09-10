package com.shinkwang.devjob.repository;

import com.shinkwang.devjob.domain.Job;
import com.shinkwang.devjob.domain.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByStatus(JobStatus status, Pageable pageable);

    Page<Job> findByTitleContaining(String keyword, Pageable pageable);

    @Query( value = """
            SELECT j FROM Job j
            JOIN FETCH j.company
            WHERE j.status = :stattus
            """,
            countQuery = "SELECT COUNT(j) FROM Job j WHERE j.status = :status"
    )
    Page<Job> findByStatusWithCompany(@Param("status") JobStatus status, Pageable pageable);

    @Query(value = """
        SELECT j FROM Job j
        JOIN FETCH j.company c
        WHERE (:keyword IS NULL OR
                LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
        AND (:location IS NULL OR c.location = :location)
        AND j.status = 'OPEN'
        """,
        countQuery = """
        SELECT COUNT(j) FROM Job j JOIN j.company c
        WHERE (:keyword IS NULL OR
                LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
        AND (:location IS NULL OR c.location = :location)
        AND j.status = 'OPEN'
        """
    )
    Page<Job> searchJobs(@Param("keyword") String keyword,
                         @Param("location") String location,
                         Pageable pageable);
}
