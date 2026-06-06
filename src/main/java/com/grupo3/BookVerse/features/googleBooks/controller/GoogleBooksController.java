package com.grupo3.BookVerse.features.googleBooks.controller;

import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBooksApiResponseDto;
import com.grupo3.BookVerse.features.googleBooks.service.GoogleBooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/google-books")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Google Books Integration",
        description = "Endpoints for consuming the Google Books external API"
)
public class GoogleBooksController {

    private final GoogleBooksService googleBooksService;

    @GetMapping("/search")
    @Operation(
            summary = "Search books using Google Books API",
            description = "Acts as an integration endpoint with the Google Books external API, returning book search results for the provided query string.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or empty query parameter", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error while calling Google Books API", content = @Content)
    })
    public ResponseEntity<GoogleBooksApiResponseDto> searchBooks(
            @Parameter(
                    description = "Query string used to search books in Google Books",
                    required = true,
                    example = "Harry Potter"
            )
            @RequestParam
            @NotBlank(message = "Query is required")
            String query
    ) {
        return ResponseEntity.ok(googleBooksService.searchBooks(query));
    }
}