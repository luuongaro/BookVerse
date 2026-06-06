package com.grupo3.BookVerse.features.googleBooks.service;

import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBooksApiResponseDto;

public interface GoogleBooksService {

    GoogleBooksApiResponseDto searchBooks(String query);
}
