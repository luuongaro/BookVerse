package com.grupo3.BookVerse.infrastructure;

public interface EmailService {

    void sendWelcomeEmail(String to, String username);
}
