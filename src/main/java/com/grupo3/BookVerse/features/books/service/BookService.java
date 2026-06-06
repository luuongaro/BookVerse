package com.grupo3.BookVerse.features.books.service;

import com.grupo3.BookVerse.features.books.dto.BookRequestDto;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponseDto createBook(BookRequestDto dto);

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookByIdExternal(UUID idExternal);

    BookResponseDto updateBook(UUID idExternal, BookRequestDto dto);

    void deleteBook(UUID idExternal);
}
