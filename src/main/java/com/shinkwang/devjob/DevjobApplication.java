package com.shinkwang.devjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevjobApplication.class, args);
    }

}
