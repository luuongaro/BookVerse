package com.grupo3.BookVerse.features.authors.dto;

import java.util.List;
import java.util.UUID;

import com.grupo3.BookVerse.features.books.domain.dto.BookDto;

public record AuthorResponseDto(

        UUID idExternal,
        String fullName,
        String nationality,
        String language,
        List<BookDto> books

) {
}
