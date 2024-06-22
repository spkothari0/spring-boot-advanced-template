package com.shreyas.spring_boot_advanced_template.service;

import com.shreyas.spring_boot_advanced_template.service.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${MAIL_SERVICE_ENABLED}")
    private boolean isMailServiceEnabled;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @param emailId
     * @param Subject
     * @param Message
     * @return
     */
    @Override
    @Async
    public CompletableFuture<Boolean> sendEmail(String emailId, String Subject, String Message) {
        try{
            if(!isMailServiceEnabled){
                logger.info("Mail service is disabled by developers.");
                return CompletableFuture.completedFuture(false);
            }
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setTo(emailId);
            helper.setSubject(Subject);
            helper.setText(Message, true);
            helper.setFrom(Constants.ApplicationEmailId);
            mailSender.send(mimeMessage);
            logger.info("Email for {} sent to {}",Subject, emailId);
            return CompletableFuture.completedFuture(true);
        }catch (MessagingException ex){
            logger.error(ex.getMessage(), ex);
            return CompletableFuture.completedFuture(false);
        }

    }
}
