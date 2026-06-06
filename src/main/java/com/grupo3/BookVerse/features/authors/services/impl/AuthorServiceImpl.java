package com.grupo3.BookVerse.features.authors.services.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.authors.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.dto.AuthorResponseDto;
import com.grupo3.BookVerse.features.authors.mapper.AuthorMapper;
import com.grupo3.BookVerse.features.authors.repository.AuthorRepository;
import com.grupo3.BookVerse.features.authors.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public AuthorResponseDto save(AuthorRequestDto authorRequestDto) {

        AuthorEntity author =
                authorMapper.toEntityDto(authorRequestDto);

        author.setIdExternal(UUID.randomUUID());

        AuthorEntity savedAuthor =
                authorRepository.save(author);

        return authorMapper.toResponseDto(savedAuthor);
    }

    @Override
    @Transactional
    public void delete(UUID authorId) {

        AuthorEntity author =
                findAuthorByIdExternal(authorId);

        authorRepository.delete(author);
    }

    @Override
    @Transactional
    public AuthorResponseDto update(
            UUID authorId,
            AuthorRequestDto authorRequestDto
    ) {

        AuthorEntity author =
                findAuthorByIdExternal(authorId);

        author.setFullName(authorRequestDto.fullName());
        author.setNationality(authorRequestDto.nationality());
        author.setLanguage(authorRequestDto.language());

        AuthorEntity updatedAuthor =
                authorRepository.save(author);

        return authorMapper.toResponseDto(updatedAuthor);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDto findById(UUID authorId) {

        AuthorEntity author =
                findAuthorByIdExternal(authorId);

        return authorMapper.toResponseDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> findAll() {

        List<AuthorEntity> authors =
                authorRepository.findAll();

        return authors.stream()
                .map(authorMapper::toResponseDto)
                .toList();
    }

    private AuthorEntity findAuthorByIdExternal(UUID authorId) {

        return authorRepository.findByIdExternal(authorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Author not found with idExternal: " + authorId
                        )
                );
    }
}