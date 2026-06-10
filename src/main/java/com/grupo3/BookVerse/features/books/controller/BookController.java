package com.grupo3.BookVerse.features.books.controller;

import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(
        name = "Books",
        description = "Endpoints for managing books stored locally in BookVerse"
)
public class BookController {

    private final BookService bookService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all books",
            description = "Retrieves all books stored locally in the system.",
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
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get book by external id",
            description = "Retrieves a locally stored book using its external UUID identifier.",
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

    @PostMapping("/google/{googleBookId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Store a Google Book locally",
            description = "Retrieves a book from Google Books API by its Google Book ID and stores it locally if it does not already exist.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book stored or retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Google Book ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found in Google Books", content = @Content)
    })
    public ResponseEntity<BookResponseDto> saveGoogleBook(
            @Parameter(
                    description = "Google Books ID of the book to persist locally",
                    required = true,
                    example = "zyTCAlFPjgYC"
            )
            @PathVariable String googleBookId
    ) {
        return ResponseEntity.ok(bookService.findOrCreateFromGoogleBookId(googleBookId));
    }
}
