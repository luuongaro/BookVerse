package com.grupo3.BookVerse.features.googleBooks.controller;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBooksApiResponseDto;
import com.grupo3.BookVerse.features.googleBooks.service.GoogleBooksService;
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

@RestController
@RequestMapping("/api/google-books")
@RequiredArgsConstructor
@Tag(
        name = "Google Books Integration",
        description = "Endpoints for consuming the Google Books external API"
)
public class GoogleBooksController {

    private final GoogleBooksService googleBooksService;

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Search books using Google Books API",
            description = "Searches books using a general query, a title, or an author.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "No books found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error while calling Google Books API", content = @Content)
    })
    public ResponseEntity<GoogleBooksApiResponseDto> searchBooks(
            @Parameter(description = "General query string", example = "Harry Potter")
            @RequestParam(required = false) String query,

            @Parameter(description = "Book title", example = "Harry Potter")
            @RequestParam(required = false) String title,

            @Parameter(description = "Book author", example = "J K Rowling")
            @RequestParam(required = false) String author
    ) {
        int providedParams = countProvidedParams(query, title, author);

        if (providedParams != 1) {
            throw new BadRequestException("You must provide exactly one search parameter: query, title, or author");
        }

        if (query != null && !query.isBlank()) {
            return ResponseEntity.ok(googleBooksService.searchBooks(query));
        }

        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(googleBooksService.searchBooksByTitle(title));
        }

        return ResponseEntity.ok(googleBooksService.searchBooksByAuthor(author));
    }

    private int countProvidedParams(String query, String title, String author) {
        int count = 0;

        if (query != null && !query.isBlank()) count++;
        if (title != null && !title.isBlank()) count++;
        if (author != null && !author.isBlank()) count++;

        return count;
    }
}
