package com.grupo3.BookVerse.features.authors.controller;

import com.grupo3.BookVerse.features.authors.services.IAuthorService;
import com.grupo3.BookVerse.features.authors.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.dto.AuthorResponseDto;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final IAuthorService authorService;

    @GetMapping
    ResponseEntity<List<AuthorResponseDto>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/{authorId}")
    ResponseEntity<AuthorResponseDto> findById(@PathVariable UUID authorId) {
        return ResponseEntity.ok(authorService.findById(authorId));
    }

    @PostMapping
    ResponseEntity<AuthorResponseDto> create(
            @RequestBody AuthorRequestDto authorRequestDto
    ) {
        return new ResponseEntity<>(
                authorService.save(authorRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{authorId}")
    ResponseEntity<AuthorResponseDto> update(
            @PathVariable UUID authorId,
            @RequestBody AuthorRequestDto authorRequestDto
    ) {
        return ResponseEntity.ok(
                authorService.update(authorId, authorRequestDto)
        );
    }

    @DeleteMapping("/{authorId}")
    ResponseEntity<Void> delete(@PathVariable UUID authorId) {
        authorService.delete(authorId);
        return ResponseEntity.noContent().build();
    }
}