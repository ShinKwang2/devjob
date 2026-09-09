package com.shinkwang.devjob.repository;

import com.shinkwang.devjob.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
