package com.shinkwang.devjob.repository;

import com.shinkwang.devjob.domain.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    boolean existsByMemberIdAndJobId(Long memberId, Long jobId);

    @Query("""
        SELECT a FROM JobApplication a
        JOIN FETCH a.job j
        JOIN FETCH j.company
        WHERE a.member.id = :memberId
        ORDER BY a.createAt DESC
    """)
    List<JobApplication> findByMemberIdWithJob(@Param("memberId") Long memberId);
}
