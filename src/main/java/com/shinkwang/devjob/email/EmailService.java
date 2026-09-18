package com.shinkwang.devjob.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.enabled:false}")
    private boolean mailEnabled;

    @Async
    public void sendJobApplicationCompletedEmail(String to, String jobTitle, String companyName) {
        if (!mailEnabled) {
            log.info("[메일 발송 생략] to={}, job={}({})", to, jobTitle, companyName);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("[DevJob] 지원 완료: " + jobTitle);
            helper.setText(buildJobApplicationCompletedHtml(jobTitle, companyName), true);

            mailSender.send(message);
            log.info("지원 완료 메일 발송: to={}", to);
        } catch (MessagingException e) {
            log.error("지원 완료 메일 발송 실패: to={}", to, e);
        }
    }

    private String buildJobApplicationCompletedHtml(String jobTitle, String companyName) {
        return """
                <div style="font-family: 'Apple SD Gothic Neo', sans-serif; line-height: 1.6;">
                  <h2>지원이 완료됐습니다 ㅣ🎉</h2>
                  <p>지원하신 공고: <strong>%s</strong> (%s)</p>
                  <p>전형 결과는 등록하신 이메일로 안내드리겠습니다.</p>
                  <hr/>
                  <p style="color:#888; font-size:12px;">본 메일은 발신 전용입니다.</p>
                </div>
                """.formatted(jobTitle, companyName);
    }
}
