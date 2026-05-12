package com.grupo3.BookVerse.features.authors.domain.dto;

import java.util.List;
import java.util.UUID;

import com.grupo3.BookVerse.features.books.domain.dto.BookDTO;

public record AuthorDTO(

        UUID idExternal,
        String fullName,
        String nationality,
        String language,
        List<BookDTO> books

) {
}
