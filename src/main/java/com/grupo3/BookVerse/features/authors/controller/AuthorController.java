package com.grupo3.BookVerse.features.authors.controller;

import com.grupo3.BookVerse.features.authors.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.dto.AuthorResponseDto;
import com.grupo3.BookVerse.features.authors.services.AuthorService;
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
@RequiredArgsConstructor
@RequestMapping("/authors")
@Tag(
        name = "Authors",
        description = "Endpoints for managing authors in BookVerse"
)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @Operation(
            summary = "Get all authors",
            description = "Retrieves a list of all registered authors.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<AuthorResponseDto>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/{authorId}")
    @Operation(
            summary = "Get author by external id",
            description = "Retrieves an author using its external UUID identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    public ResponseEntity<AuthorResponseDto> findById(
            @Parameter(
                    description = "External UUID of the author",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID authorId
    ) {
        return ResponseEntity.ok(authorService.findById(authorId));
    }

    @PostMapping
    @Operation(
            summary = "Create a new author",
            description = "Creates a new author and returns the created resource.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<AuthorResponseDto> create(
            @Valid @RequestBody AuthorRequestDto authorRequestDto
    ) {
        return new ResponseEntity<>(
                authorService.save(authorRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{authorId}")
    @Operation(
            summary = "Update an author",
            description = "Updates the information of an existing author identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    public ResponseEntity<AuthorResponseDto> update(
            @Parameter(
                    description = "External UUID of the author to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID authorId,
            @Valid @RequestBody AuthorRequestDto authorRequestDto
    ) {
        return ResponseEntity.ok(
                authorService.update(authorId, authorRequestDto)
        );
    }

    @DeleteMapping("/{authorId}")
    @Operation(
            summary = "Delete an author",
            description = "Deletes an author identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "External UUID of the author to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID authorId
    ) {
        authorService.delete(authorId);
        return ResponseEntity.noContent().build();
    }
}