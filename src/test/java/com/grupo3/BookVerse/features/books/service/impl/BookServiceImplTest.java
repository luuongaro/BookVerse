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

    //verificar que el service traiga todos los libros correctamente
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

        when(bookRepository.findByDeletedFalse())
                .thenReturn(List.of(book));

        when(bookMapper.toResponseDtoList(anyList()))
                .thenReturn(List.of(dto));

        List<BookResponseDto> result =
                bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(
                "Harry Potter",
                result.get(0).getTitle()
        );

        verify(bookRepository)
                .findByDeletedFalse();

        verify(bookMapper)
                .toResponseDtoList(anyList());
    }

    //obtener libros por id
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

    //excepción si no existe el libro
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

    //si ya existe, no consulta google ni guarda
    //prueba que devuelva el libro, que no vuelva a guardarlo y que no llame a google otra vez
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

    //si no existe, consulta google y lo guarda
    //prueba que busca el libro en google, que lo guarda y que lo devuelve
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