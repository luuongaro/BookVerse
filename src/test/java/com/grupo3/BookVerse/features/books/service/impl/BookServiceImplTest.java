package com.grupo3.BookVerse.features.books.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.mapper.BookMapper;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBookItemDto;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBookVolumeDto;
import com.grupo3.BookVerse.features.googleBooks.service.GoogleBooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private GoogleBooksService googleBooksService;

    @InjectMocks
    private BookServiceImpl bookService;

    // Verify that the service returns all books correctly
    @Test
    void shouldReturnAllBooks() {

        BookEntity book = BookEntity.builder()
                .title("Harry Potter")
                .deleted(false)
                .build();

        BookResponseDto dto =
                BookResponseDto.builder()
                        .title("Harry Potter")
                        .build();

        Pageable pageable =
                PageRequest.of(0, 10);

        Page<BookEntity> booksPage =
                new PageImpl<>(List.of(book));

        Page<BookResponseDto> dtoPage =
                new PageImpl<>(List.of(dto));

        when(bookRepository.findByDeletedFalse(any(Pageable.class)))
                .thenReturn(booksPage);

        when(bookMapper.toResponseDtoList(anyList()))
                .thenReturn(List.of(dto));

        Page<BookResponseDto> result =
                bookService.getAllBooks(pageable);

        assertNotNull(result);

        assertEquals(
                1,
                result.getContent().size()
        );

        assertEquals(
                "Harry Potter",
                result.getContent().get(0).getTitle()
        );

        verify(bookRepository)
                .findByDeletedFalse(any(Pageable.class));

        verify(bookMapper)
                .toResponseDtoList(anyList());
    }

    // Verify that a book is returned by external ID
    @Test
    void shouldReturnBookByIdExternal() {
        UUID id = UUID.randomUUID();

        BookEntity book = BookEntity.builder()
                .idExternal(id)
                .title("Clean Code")
                .deleted(false)
                .build();

        BookResponseDto dto =
                BookResponseDto.builder()
                        .title("Clean Code")
                        .build();

        when(bookRepository.findByIdExternal(id))
                .thenReturn(Optional.of(book));

        when(bookMapper.toResponseDto(book))
                .thenReturn(dto);

        BookResponseDto result =
                bookService.getBookByIdExternal(id);

        assertNotNull(result);

        assertEquals(
                "Clean Code",
                result.getTitle()
        );

        verify(bookRepository)
                .findByIdExternal(id);

        verify(bookMapper)
                .toResponseDto(book);
    }

    // Verify that an exception is thrown when the book does not exist
    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        UUID id = UUID.randomUUID();

        when(bookRepository.findByIdExternal(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> bookService.getBookByIdExternal(id)
        );

        verify(bookRepository)
                .findByIdExternal(id);
    }

    // Verify that if the book already exists, Google API is not called and the book is not saved again
    @Test
    void shouldReturnExistingBookIfAlreadyStored() {
        String googleBookId = "abc123";

        BookEntity existingBook =
                BookEntity.builder()
                        .googleBookId(googleBookId)
                        .title("Existing Book")
                        .deleted(false)
                        .build();

        BookResponseDto dto =
                BookResponseDto.builder()
                        .title("Existing Book")
                        .build();

        when(bookRepository.findByGoogleBookId(googleBookId))
                .thenReturn(Optional.of(existingBook));

        when(bookMapper.toResponseDto(existingBook))
                .thenReturn(dto);

        BookResponseDto result =
                bookService.findOrCreateFromGoogleBookId(googleBookId);

        assertNotNull(result);

        assertEquals(
                "Existing Book",
                result.getTitle()
        );

        verify(bookRepository)
                .findByGoogleBookId(googleBookId);

        verify(bookRepository, never())
                .save(any());

        verify(googleBooksService, never())
                .getBookByGoogleId(anyString());
    }

    // Verify that if the book does not exist, it is fetched from Google, saved, and returned
    @Test
    void shouldCreateBookWhenNotExists() {

        String googleBookId = "google123";

        GoogleBookItemDto volumeInfo =
                new GoogleBookItemDto();

        volumeInfo.setTitle("Spring Boot");
        volumeInfo.setAuthors(List.of("Craig Walls"));
        volumeInfo.setCategories(List.of("Programming"));
        volumeInfo.setPublisher("Manning");
        volumeInfo.setPublishedDate("2024");
        volumeInfo.setLanguage("en");

        GoogleBookVolumeDto googleBook =
                new GoogleBookVolumeDto();

        googleBook.setId(googleBookId);
        googleBook.setVolumeInfo(volumeInfo);

        BookEntity savedBook =
                BookEntity.builder()
                        .googleBookId(googleBookId)
                        .title("Spring Boot")
                        .authors(Set.of("Craig Walls"))
                        .deleted(false)
                        .build();

        BookResponseDto dto =
                BookResponseDto.builder()
                        .title("Spring Boot")
                        .build();

        when(bookRepository.findByGoogleBookId(googleBookId))
                .thenReturn(Optional.empty());

        when(googleBooksService.getBookByGoogleId(googleBookId))
                .thenReturn(googleBook);

        when(bookRepository.save(any(BookEntity.class)))
                .thenReturn(savedBook);

        when(bookMapper.toResponseDto(savedBook))
                .thenReturn(dto);

        BookResponseDto result =
                bookService.findOrCreateFromGoogleBookId(googleBookId);

        assertNotNull(result);

        assertEquals(
                "Spring Boot",
                result.getTitle()
        );

        verify(googleBooksService)
                .getBookByGoogleId(googleBookId);

        verify(bookRepository)
                .save(any(BookEntity.class));

        verify(bookMapper)
                .toResponseDto(savedBook);
    }
}