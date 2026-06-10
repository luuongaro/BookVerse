package com.grupo3.BookVerse.features.notifications.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.grupo3.BookVerse.features.notifications.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Override
    @Async("emailTaskExecutor")
    public void sendWelcomeEmail(String to, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Welcome to BookVerse fellow reader!");
            message.setText(
                    "Hi " + username + ",\n\n" +
                    "Welcome to BookVerse! Your account has been created successfully.\n\n" +
                    "Happy reading,\n" +
                    "The BookVerse Team"
            );

            mailSender.send(message);
        } catch (Exception exception) {
            log.error("Failed to send welcome email to {}", to, exception);
        }
    }
}