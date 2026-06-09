package com.grupo3.BookVerse.features.books.service;

import com.grupo3.BookVerse.features.books.dto.BookResponseDto;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookByIdExternal(UUID idExternal);

    BookResponseDto findOrCreateFromGoogleBookId(String googleBookId);
}