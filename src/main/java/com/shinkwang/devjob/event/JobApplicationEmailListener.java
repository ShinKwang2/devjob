package com.shinkwang.devjob.event;

import com.shinkwang.devjob.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class JobApplicationEmailListener {

    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendEmailAfterJobApply(JobApplicationCompletedEvent event) {
        emailService.sendJobApplicationCompletedEmail(
                event.recipientEmail(),
                event.jobTitle(),
                event.companyName()
        );
    }
}
