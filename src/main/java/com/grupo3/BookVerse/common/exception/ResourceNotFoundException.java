package com.grupo3.BookVerse.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
            super(message);
        }
    }
