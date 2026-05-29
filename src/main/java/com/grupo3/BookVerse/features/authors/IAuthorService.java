package com.grupo3.BookVerse.features.authors;


import com.grupo3.BookVerse.features.authors.domain.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.domain.dto.AuthorResponseDto;

import java.util.List;
import java.util.UUID;

public interface IAuthorService {

    AuthorResponseDto save(AuthorRequestDto authorRequestDto);

    void delete(UUID authorId);

    AuthorResponseDto update(UUID authorId, AuthorRequestDto authorRequestDto);

    AuthorResponseDto findById(UUID authorId);

    List<AuthorResponseDto> findAll();
}