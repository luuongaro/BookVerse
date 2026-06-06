package com.grupo3.BookVerse.features.books.service.impl;

import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.dto.BookRequestDto;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.mapper.BookMapper;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDto createBook(BookRequestDto dto) {

        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new DuplicateResourceException("ISBN is already in use");
        }

        BookEntity book = bookMapper.toEntity(dto);

        book.setIdExternal(UUID.randomUUID());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setIsDeleted(false);

        try {
            BookEntity saved = bookRepository.save(book);
            return bookMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("ISBN is already in use");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {

        List<BookEntity> books =
                bookRepository.findAll()
                        .stream()
                        .filter(book -> Boolean.FALSE.equals(book.getIsDeleted()))
                        .toList();

        return bookMapper.toResponseDtoList(books);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBookByIdExternal(UUID idExternal) {

        BookEntity book = findBookByIdExternal(idExternal);

        return bookMapper.toResponseDto(book);
    }

    @Override
    @Transactional
    public BookResponseDto updateBook(UUID idExternal, BookRequestDto dto) {

        BookEntity existing = findBookByIdExternal(idExternal);

        bookMapper.updateEntityFromDto(dto, existing);

        existing.setUpdatedAt(LocalDateTime.now());

        BookEntity saved = bookRepository.save(existing);

        return bookMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteBook(UUID idExternal) {

        BookEntity existing = findBookByIdExternal(idExternal);

        existing.setIsDeleted(true);
        existing.setUpdatedAt(LocalDateTime.now());

        bookRepository.save(existing);
    }

    private BookEntity findBookByIdExternal(UUID idExternal) {

        return bookRepository.findByIdExternal(idExternal)
                .filter(book -> Boolean.FALSE.equals(book.getIsDeleted()))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: " + idExternal
                        )
                );
    }
}


