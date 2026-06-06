package com.grupo3.BookVerse.features.books.controller;

import com.grupo3.BookVerse.features.books.dto.BookRequestDto;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponseDto createBook(@Valid @RequestBody BookRequestDto dto) {
        return bookService.createBook(dto);
    }

    @GetMapping
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{idExternal}")
    public BookResponseDto getBookByIdExternal(@PathVariable UUID idExternal) {
        return bookService.getBookByIdExternal(idExternal);
    }

    @PutMapping("/{idExternal}")
    public BookResponseDto updateBook(@PathVariable UUID idExternal,
                                      @Valid @RequestBody BookRequestDto dto) {
        return bookService.updateBook(idExternal, dto);
    }

    @DeleteMapping("/{idExternal}")
    public void deleteBook(@PathVariable UUID idExternal) {
        bookService.deleteBook(idExternal);
    }
}
