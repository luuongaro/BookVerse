package com.grupo3.BookVerse.features.chapters.controller;

import com.grupo3.BookVerse.features.chapters.dto.ChapterRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;
import com.grupo3.BookVerse.features.chapters.service.ChapterService;
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
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
@Tag(
        name = "Chapters",
        description = "Endpoints for managing chapters in BookVerse"
)
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping
    @Operation(
            summary = "Get all chapters",
            description = "Retrieves a list of all chapters registered in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chapters retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<ChapterResponseDto>> getAllChapters() {
        return ResponseEntity.ok(chapterService.getAllChapters());
    }

    @GetMapping("/{idExternal}")
    @Operation(
            summary = "Get chapter by external id",
            description = "Retrieves a chapter using its external UUID identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chapter retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Chapter not found", content = @Content)
    })
    public ResponseEntity<ChapterResponseDto> getChapterByIdExternal(
            @Parameter(
                    description = "External UUID of the chapter",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        return ResponseEntity.ok(
                chapterService.getChapterByIdExternal(idExternal)
        );
    }

    @PostMapping
    @Operation(
            summary = "Create a new chapter",
            description = "Creates a new chapter associated with a story.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chapter created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Referenced story not found", content = @Content)
    })
    public ResponseEntity<ChapterResponseDto> createChapter(
            @Valid @RequestBody ChapterRequestDto dto
    ) {
        return new ResponseEntity<>(
                chapterService.createChapter(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{idExternal}")
    @Operation(
            summary = "Update a chapter",
            description = "Updates an existing chapter identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chapter updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Chapter not found", content = @Content)
    })
    public ResponseEntity<ChapterResponseDto> updateChapter(
            @Parameter(
                    description = "External UUID of the chapter to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal,
            @Valid @RequestBody ChapterRequestDto dto
    ) {
        return ResponseEntity.ok(
                chapterService.updateChapter(idExternal, dto)
        );
    }

    @DeleteMapping("/{idExternal}")
    @Operation(
            summary = "Delete a chapter",
            description = "Deletes a chapter identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Chapter deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Chapter not found", content = @Content)
    })
    public ResponseEntity<Void> deleteChapter(
            @Parameter(
                    description = "External UUID of the chapter to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        chapterService.deleteChapter(idExternal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/story/{storyId}")
    @Operation(
            summary = "Get chapters by story",
            description = "Retrieves all chapters associated with a specific story.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chapters retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Story not found", content = @Content)
    })
    public ResponseEntity<List<ChapterResponseDto>> getChaptersByStoryId(
            @Parameter(
                    description = "External UUID of the story",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID storyId
    ) {
        return ResponseEntity.ok(
                chapterService.getChaptersByStoryId(storyId)
        );
    }
}

