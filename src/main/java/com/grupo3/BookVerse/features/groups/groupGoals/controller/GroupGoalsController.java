package com.grupo3.BookVerse.features.groups.groupGoals.controller;

import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsResponseDto;
import com.grupo3.BookVerse.features.groups.groupGoals.service.GroupGoalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/group-goals")
@Tag(
        name = "Group Goals",
        description = "Endpoints for managing reading goals within reading groups"
)
public class GroupGoalsController {

    private final GroupGoalsService groupGoalsService;

    @GetMapping
    @Operation(
            summary = "Get all group goals",
            description = "Retrieves all group goals registered in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group goals retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<GroupGoalsResponseDto>> findAll() {
        return ResponseEntity.ok(groupGoalsService.findAll());
    }

    @GetMapping("/{groupGoalsId}")
    @Operation(
            summary = "Get group goal by external id",
            description = "Retrieves a specific group goal by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group goal retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group goal not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> findById(
            @Parameter(
                    description = "External UUID of the group goal",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupGoalsId
    ) {
        return ResponseEntity.ok(groupGoalsService.findById(groupGoalsId));
    }

    @PostMapping
    @Operation(
            summary = "Create a new group goal",
            description = "Creates a new reading goal associated with a reading group.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group goal created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> create(
            @Valid @RequestBody GroupGoalsRequestDto groupGoalsRequestDto
    ) {
        return new ResponseEntity<>(
                groupGoalsService.save(groupGoalsRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{groupGoalsId}")
    @Operation(
            summary = "Update a group goal",
            description = "Updates an existing group goal by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group goal updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group goal or reading group not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> update(
            @Parameter(
                    description = "External UUID of the group goal to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupGoalsId,
            @Valid @RequestBody GroupGoalsRequestDto groupGoalsRequestDto
    ) {
        return ResponseEntity.ok(
                groupGoalsService.update(groupGoalsId, groupGoalsRequestDto)
        );
    }

    @DeleteMapping("/{groupGoalsId}")
    @Operation(
            summary = "Delete a group goal",
            description = "Deletes a group goal by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group goal deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group goal not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "External UUID of the group goal to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupGoalsId
    ) {
        groupGoalsService.delete(groupGoalsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/group/{groupId}")
    @Operation(
            summary = "Get group goal by reading group",
            description = "Retrieves the active goal associated with a specific reading group using its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group goal retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group or goal not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> findByGroupId(
            @Parameter(
                    description = "External UUID of the reading group",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupId
    ) {
        return ResponseEntity.ok(
                groupGoalsService.findByGroupId(groupId)
        );
    }
}
