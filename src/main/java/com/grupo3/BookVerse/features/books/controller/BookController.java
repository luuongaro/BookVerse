package com.grupo3.BookVerse.features.books.controller;

import com.grupo3.BookVerse.features.books.dto.BookRequestDto;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(
        name = "Books",
        description = "Endpoints for managing books in BookVerse"
)
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(
            summary = "Get all books",
            description = "Retrieves a list of all books registered in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{idExternal}")
    @Operation(
            summary = "Get book by external id",
            description = "Retrieves a book using its external UUID identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    public ResponseEntity<BookResponseDto> getBookByIdExternal(
            @Parameter(
                    description = "External UUID of the book",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        return ResponseEntity.ok(bookService.getBookByIdExternal(idExternal));
    }

    @PostMapping
    @Operation(
            summary = "Create a new book",
            description = "Creates a new book and returns the created resource.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Referenced author or related resource not found", content = @Content)
    })
    public ResponseEntity<BookResponseDto> createBook(
            @Valid @RequestBody BookRequestDto dto
    ) {
        return new ResponseEntity<>(
                bookService.createBook(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{idExternal}")
    @Operation(
            summary = "Update a book",
            description = "Updates an existing book identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    public ResponseEntity<BookResponseDto> updateBook(
            @Parameter(
                    description = "External UUID of the book to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal,
            @Valid @RequestBody BookRequestDto dto
    ) {
        return ResponseEntity.ok(
                bookService.updateBook(idExternal, dto)
        );
    }

    @DeleteMapping("/{idExternal}")
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(
                    description = "External UUID of the book to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        bookService.deleteBook(idExternal);
        return ResponseEntity.noContent().build();
    }
}


