package com.grupo3.BookVerse.features.authors.controller;

import com.grupo3.BookVerse.features.authors.services.AuthorService;
import com.grupo3.BookVerse.features.authors.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.dto.AuthorResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
@Tag(
        name = "Authors",
        description = "Endpoints for managing authors in BookVerse"
)
@SecurityRequirement(name = "bearerAuth")


public class AuthorController {

    private final AuthorService authorService;

    @Operation(
            summary = "Get all authors",
            description = "Retrieves a list of all registered authors."
    )


    @GetMapping
    ResponseEntity<List<AuthorResponseDto>> findAll() {

        return ResponseEntity.ok(authorService.findAll());
    }
    @Operation(
            summary = "Get author by ID",
            description = "Retrieves an author using its external UUID identifier."
    )


    @GetMapping("/{authorId}")
    ResponseEntity<AuthorResponseDto> findById(@PathVariable UUID authorId) {
        return ResponseEntity.ok(authorService.findById(authorId));
    }
    @Operation(
            summary = "Create author",
            description = "Creates a new author and returns the created resource."
    )


    @PostMapping
    ResponseEntity<AuthorResponseDto> create(
            @RequestBody AuthorRequestDto authorRequestDto
    ) {
        return new ResponseEntity<>(
                authorService.save(authorRequestDto),
                HttpStatus.CREATED
        );
    }
    @Operation(
            summary = "Update author",
            description = "Updates the information of an existing author identified by its UUID."
    )


    @PutMapping("/{authorId}")
    ResponseEntity<AuthorResponseDto> update(
            @PathVariable UUID authorId,
            @RequestBody AuthorRequestDto authorRequestDto
    ) {
        return ResponseEntity.ok(
                authorService.update(authorId, authorRequestDto)
        );
    }
    @Operation(
            summary = "Delete author",
            description = "Deletes an author identified by its UUID."
    )


    @DeleteMapping("/{authorId}")
    ResponseEntity<Void> delete(@PathVariable UUID authorId) {
        authorService.delete(authorId);
        return ResponseEntity.noContent().build();
    }
}