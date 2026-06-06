package com.grupo3.BookVerse.features.groups.groupProgress.controller;

import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressRequestDto;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;
import com.grupo3.BookVerse.features.groups.groupProgress.service.GroupProgressService;
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
@RequestMapping("/api/group-progress")
@RequiredArgsConstructor
@Tag(
        name = "Group Progress",
        description = "Endpoints for managing reading progress within reading groups"
)
public class GroupProgressController {

    private final GroupProgressService groupProgressService;

    @PostMapping
    @Operation(
            summary = "Create or update group progress",
            description = "Creates a new reading progress record for a user in a reading group, or updates the existing one if it already exists.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group progress created or updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group or user not found", content = @Content)
    })
    public ResponseEntity<GroupProgressResponseDto> createOrUpdateProgress(
            @Valid @RequestBody GroupProgressRequestDto requestDto) {

        return new ResponseEntity<>(
                groupProgressService.createOrUpdateProgress(requestDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all group progress records",
            description = "Retrieves all reading progress records ordered by the most recently updated first.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progress records retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<GroupProgressResponseDto>> getAllProgress() {
        return ResponseEntity.ok(groupProgressService.getAllProgress());
    }

    @GetMapping("/{idExternal}")
    @Operation(
            summary = "Get group progress by external id",
            description = "Retrieves a specific group progress record by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group progress retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group progress not found", content = @Content)
    })
    public ResponseEntity<GroupProgressResponseDto> getProgressByIdExternal(
            @Parameter(
                    description = "External UUID of the group progress record",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal) {

        return ResponseEntity.ok(
                groupProgressService.getProgressByIdExternal(idExternal)
        );
    }

    @GetMapping("/group/{groupId}")
    @Operation(
            summary = "Get progress by reading group",
            description = "Retrieves all progress records associated with a specific reading group using its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group progress records retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group not found", content = @Content)
    })
    public ResponseEntity<List<GroupProgressResponseDto>> getProgressByGroupId(
            @Parameter(
                    description = "External UUID of the reading group",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupId) {

        return ResponseEntity.ok(
                groupProgressService.getProgressByGroupId(groupId)
        );
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get progress by user",
            description = "Retrieves all progress records associated with a specific user using the user's external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User progress records retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<List<GroupProgressResponseDto>> getProgressByUserId(
            @Parameter(
                    description = "External UUID of the user",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                groupProgressService.getProgressByUserId(userId)
        );
    }

    @DeleteMapping("/{idExternal}")
    @Operation(
            summary = "Delete group progress",
            description = "Deletes a group progress record by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group progress deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group progress not found", content = @Content)
    })
    public ResponseEntity<Void> deleteProgress(
            @Parameter(
                    description = "External UUID of the group progress record to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal) {

        groupProgressService.deleteProgress(idExternal);
        return ResponseEntity.noContent().build();
    }
}

