package com.grupo3.BookVerse.features.groups.groupComment.controller;

import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentRequestDto;
import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentResponseDto;
import com.grupo3.BookVerse.features.groups.groupComment.service.GroupCommentService;
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
@RequestMapping("/group-comments")
@Tag(
        name = "Group Comments",
        description = "Endpoints for managing comments in reading groups"
)
public class GroupCommentController {

    private final GroupCommentService groupCommentService;

    @GetMapping
    @Operation(
            summary = "Get all group comments",
            description = "Retrieves all visible group comments ordered by creation date.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<GroupCommentResponseDto>> findAll() {
        return ResponseEntity.ok(groupCommentService.findAll());
    }

    @GetMapping("/{commentId}")
    @Operation(
            summary = "Get group comment by external id",
            description = "Retrieves a visible group comment by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
    })
    public ResponseEntity<GroupCommentResponseDto> findById(
            @Parameter(
                    description = "External UUID of the group comment",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID commentId
    ) {
        return ResponseEntity.ok(groupCommentService.findById(commentId));
    }

    @PostMapping
    @Operation(
            summary = "Create a new group comment",
            description = "Creates a new comment associated with a reading group and a user.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group or user not found", content = @Content)
    })
    public ResponseEntity<GroupCommentResponseDto> create(
            @Valid @RequestBody GroupCommentRequestDto groupCommentRequestDto
    ) {
        return new ResponseEntity<>(
                groupCommentService.save(groupCommentRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{commentId}")
    @Operation(
            summary = "Update a group comment",
            description = "Updates an existing visible group comment by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment, reading group or user not found", content = @Content)
    })
    public ResponseEntity<GroupCommentResponseDto> update(
            @Parameter(
                    description = "External UUID of the group comment to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID commentId,
            @Valid @RequestBody GroupCommentRequestDto groupCommentRequestDto
    ) {
        return ResponseEntity.ok(
                groupCommentService.update(commentId, groupCommentRequestDto)
        );
    }

    @DeleteMapping("/{commentId}")
    @Operation(
            summary = "Delete a group comment",
            description = "Performs a logical deletion of a group comment by marking it as hidden.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "External UUID of the group comment to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID commentId
    ) {
        groupCommentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/group/{groupId}")
    @Operation(
            summary = "Get comments by reading group",
            description = "Retrieves all visible comments associated with a specific reading group.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group not found", content = @Content)
    })
    public ResponseEntity<List<GroupCommentResponseDto>> findByGroupId(
            @Parameter(
                    description = "External UUID of the reading group",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupId
    ) {
        return ResponseEntity.ok(groupCommentService.findByGroupId(groupId));
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get comments by user",
            description = "Retrieves all visible comments created by a specific user.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<List<GroupCommentResponseDto>> findByUserId(
            @Parameter(
                    description = "External UUID of the user",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(groupCommentService.findByUserId(userId));
    }
}
