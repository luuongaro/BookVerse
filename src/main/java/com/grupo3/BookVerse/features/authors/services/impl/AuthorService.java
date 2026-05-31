package com.grupo3.BookVerse.features.authors.services.impl;


import com.grupo3.BookVerse.common.EntityNotFoundException;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.authors.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.dto.AuthorResponseDto;
import com.grupo3.BookVerse.features.authors.mapper.AuthorMapper;

import com.grupo3.BookVerse.features.authors.repository.AuthorRepository;
import com.grupo3.BookVerse.features.authors.services.IAuthorService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponseDto save(AuthorRequestDto authorRequestDto) {

        AuthorEntity author = authorMapper.toEntityDto(authorRequestDto);

        author.setIdExternal(UUID.randomUUID());

        AuthorEntity saved = authorRepository.save(author);

        return authorMapper.toResponseDto(saved);
    }

    @Override
    public void delete(UUID authorId) {

        AuthorEntity author = authorRepository.findByIdExternal(authorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author",
                        "Author was not found",
                        "authorId",
                        authorId.toString()
                ));

        authorRepository.delete(author);
    }

    @Override
    public AuthorResponseDto update(UUID authorId,
                                    AuthorRequestDto authorRequestDto) {

        AuthorEntity author = authorRepository.findByIdExternal(authorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author",
                        "Author was not found",
                        "authorId",
                        authorId.toString()
                ));

        author.setFullName(authorRequestDto.fullName());
        author.setNationality(authorRequestDto.nationality());
        author.setLanguage(authorRequestDto.language());

        AuthorEntity saved = authorRepository.save(author);

        return authorMapper.toResponseDto(saved);
    }

    @Override
    public AuthorResponseDto findById(UUID authorId) {
        return authorRepository.findByIdExternal(authorId)
                .map(authorMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author",
                        "Author was not found",
                        "authorId",
                        authorId.toString()
                ));


    }

    @Override
    public List<AuthorResponseDto> findAll() {

        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toResponseDto)
                .toList();
    }
}