package com.shinkwang.devjob.event;

public record JobApplicationSubmittedEvent(
        String recipientEmail,
        String jobTitle,
        String companyName
) {
}
