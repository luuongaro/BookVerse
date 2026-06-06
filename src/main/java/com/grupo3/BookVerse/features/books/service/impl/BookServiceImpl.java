package com.grupo3.BookVerse.features.books.service.impl;

import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.dto.BookRequestDto;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.mapper.BookMapper;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.books.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    // Creates a new book after validating the ISBN is not already in use.
    public BookResponseDto createBook(BookRequestDto dto) {

        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new DuplicateResourceException("ISBN is already in use");
        }

        BookEntity entity = bookMapper.toEntity(dto);
        entity.setIdExternal(UUID.randomUUID());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setIsDeleted(false);

        try {
            BookEntity saved = bookRepository.saveAndFlush(entity);
            return bookMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            // Backstop for a concurrent insert that races past the check above
            // and hits the UNIQUE constraint on isbn at the database level.
            throw new DuplicateResourceException("ISBN is already in use");
        }
    }

    @Override
    // Retrieves all books and maps them to response DTOs.
    public List<BookResponseDto> getAllBooks() {
        List<BookEntity> entities = bookRepository.findAll();
        return bookMapper.toResponseDtoList(entities);
    }

    @Override
    // Retrieves a book by external ID and maps it to a response DTO.
    public BookResponseDto getBookByIdExternal(UUID idExternal) {
        BookEntity book = bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return bookMapper.toResponseDto(book);
    }

    @Override
    @Transactional
    // Updates an existing book after validating that it exists.
    public BookResponseDto updateBook(UUID idExternal, BookRequestDto dto) {
        BookEntity existing = bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        bookMapper.updateEntityFromDto(dto, existing);
        existing.setUpdatedAt(LocalDateTime.now());

        BookEntity saved = bookRepository.save(existing);
        return bookMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    // Deletes a book by external ID after validating that it exists.
    public void deleteBook(UUID idExternal) {
        BookEntity existing = bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        bookRepository.delete(existing);
    }
}
