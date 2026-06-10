package com.grupo3.BookVerse.features.notifications.service;

public interface EmailService {

    void sendWelcomeEmail(String to, String username);
}
