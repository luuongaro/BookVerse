package com.grupo3.BookVerse.features.books.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.mapper.BookMapper;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        return bookMapper.toResponseDtoList(bookRepository.findByDeletedFalse());
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBookByIdExternal(UUID idExternal) {
        BookEntity book = findBookByIdExternal(idExternal);
        return bookMapper.toResponseDto(book);
    }

    private BookEntity findBookByIdExternal(UUID idExternal) {
        return bookRepository.findByIdExternal(idExternal)
                .filter(book -> Boolean.FALSE.equals(book.getDeleted()))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: " + idExternal
                        )
                );
    }
}