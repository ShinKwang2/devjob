package com.shinkwang.devjob.event;

public record JobApplicationCompletedEvent(
        String recipientEmail,
        String jobTitle,
        String companyName
) {
}
