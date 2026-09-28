package com.grupo3.BookVerse.features.googleBooks.service;

import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBookVolumeDto;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBooksApiResponseDto;

public interface GoogleBooksService {

    GoogleBooksApiResponseDto searchBooks(String query);

    GoogleBooksApiResponseDto searchBooksByTitle(String title);

    GoogleBooksApiResponseDto searchBooksByAuthor(String author);

    GoogleBookVolumeDto getBookByGoogleId(String googleBookId);
}