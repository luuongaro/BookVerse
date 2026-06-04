package com.grupo3.BookVerse.features.authors.services;


import com.grupo3.BookVerse.features.authors.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.dto.AuthorResponseDto;

import java.util.List;
import java.util.UUID;

public interface AuthorService {

    AuthorResponseDto save(AuthorRequestDto authorRequestDto);

    void delete(UUID authorId);

    AuthorResponseDto update(UUID authorId, AuthorRequestDto authorRequestDto);

    AuthorResponseDto findById(UUID authorId);

    List<AuthorResponseDto> findAll();
}