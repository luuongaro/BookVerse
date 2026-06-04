package com.grupo3.BookVerse.features.authors.services.impl;




import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
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
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Author was not found with idExternal: " + authorId
                    ));

        authorRepository.delete(author);
    }

    @Override
    public AuthorResponseDto update(UUID authorId,
                                    AuthorRequestDto authorRequestDto) {

        AuthorEntity author = authorRepository.findByIdExternal(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with idExternal: " + authorId
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with idExternal: " + authorId
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