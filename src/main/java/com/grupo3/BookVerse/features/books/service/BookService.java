package com.grupo3.BookVerse.features.books.service;

import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookService {

    Page<BookResponseDto> getAllBooks(Pageable pageable);

    BookResponseDto getBookByIdExternal(UUID idExternal);

    BookResponseDto findOrCreateFromGoogleBookId(String googleBookId);
}