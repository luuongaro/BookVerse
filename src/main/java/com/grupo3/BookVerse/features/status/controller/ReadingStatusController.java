package com.grupo3.BookVerse.features.status.controller;

import com.grupo3.BookVerse.features.status.dto.ReadingStatusRequestDto;
import com.grupo3.BookVerse.features.status.dto.ReadingStatusResponseDto;
import com.grupo3.BookVerse.features.status.service.ReadingStatusService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reading-statuses")
@RequiredArgsConstructor
@Tag(name = "Reading Statuses", description = "Endpoints for managing user reading statuses for books and stories")
public class ReadingStatusController {

    private final ReadingStatusService readingStatusService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new reading status", description = "Creates a new reading status for the authenticated user associated with either a book or a story.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reading status created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or limit reached", content = @Content),
            @ApiResponse(responseCode = "409", description = "Reading status already exists for this content", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book or story not found", content = @Content)
    })
    public ResponseEntity<ReadingStatusResponseDto> createReadingStatus(@Valid @RequestBody ReadingStatusRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readingStatusService.createReadingStatus(requestDto));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all reading statuses", description = "Retrieves all reading statuses registered in the system.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reading statuses retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public ResponseEntity<List<ReadingStatusResponseDto>> getAllReadingStatuses() {
        return ResponseEntity.ok(readingStatusService.getAllReadingStatuses());
    }

    @GetMapping("/{idExternal}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get reading status by external id", description = "Retrieves a specific reading status by its external UUID.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reading status retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading status not found", content = @Content)
    })
    public ResponseEntity<ReadingStatusResponseDto> getReadingStatusByIdExternal(@Parameter(description = "External UUID", required = true) @PathVariable UUID idExternal) {
        return ResponseEntity.ok(readingStatusService.getReadingStatusByIdExternal(idExternal));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get reading statuses by user", description = "Retrieves all reading statuses for a specific user.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reading statuses retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<List<ReadingStatusResponseDto>> getReadingStatusesByUser(@Parameter(description = "User UUID", required = true) @PathVariable UUID userId) {
        return ResponseEntity.ok(readingStatusService.getReadingStatusesByUser(userId));
    }

    @PutMapping("/{idExternal}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update a reading status", description = "Updates an existing reading status.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reading status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplicate reading status for content", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<ReadingStatusResponseDto> updateReadingStatus(@Parameter(description = "External UUID", required = true) @PathVariable UUID idExternal, @Valid @RequestBody ReadingStatusRequestDto requestDto) {
        return ResponseEntity.ok(readingStatusService.updateReadingStatus(idExternal, requestDto));
    }

    @DeleteMapping("/{idExternal}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a reading status", description = "Deletes a reading status.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reading status deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading status not found", content = @Content)
    })
    public ResponseEntity<Void> deleteReadingStatus(@Parameter(description = "External UUID", required = true) @PathVariable UUID idExternal) {
        readingStatusService.deleteReadingStatus(idExternal);
        return ResponseEntity.noContent().build();
    }
}