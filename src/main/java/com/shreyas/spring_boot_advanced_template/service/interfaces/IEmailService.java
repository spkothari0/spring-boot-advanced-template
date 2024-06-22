package com.shreyas.spring_boot_advanced_template.service.interfaces;

import java.util.concurrent.CompletableFuture;

public interface IEmailService {
    CompletableFuture<Boolean> sendEmail(String emailId, String Subject, String Message);
}
