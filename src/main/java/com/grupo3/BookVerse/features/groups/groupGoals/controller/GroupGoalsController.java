package com.grupo3.BookVerse.features.groups.groupGoals.controller;

import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalStatusRequestDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group-goals")
@Tag(
        name = "Group Goals",
        description = "Endpoints for managing reading goals within reading groups"
)
public class GroupGoalsController {

    private final GroupGoalsService groupGoalsService;

    @PostMapping("/group/{groupId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a new group goal",
            description = """
                    Creates a new goal for a reading group.
                    A group can only have one ACTIVE goal at a time.
                    Only the group creator, an admin, or a moderator can perform this action.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group goal created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or an active goal already exists", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> createGoal(
            @Parameter(
                    description = "External UUID of the reading group",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupId,
            @Valid @RequestBody GroupGoalsRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(groupGoalsService.createGoal(groupId, requestDto));
    }

    @GetMapping("/{goalId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get group goal by external id",
            description = "Retrieves a specific group goal by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group goal retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group goal not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> getGoalByIdExternal(
            @Parameter(
                    description = "External UUID of the group goal",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID goalId
    ) {
        return ResponseEntity.ok(
                groupGoalsService.getGoalByIdExternal(goalId)
        );
    }

    @GetMapping("/group/{groupId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all goals by reading group",
            description = "Retrieves all goals associated with a reading group ordered by last update.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group goals retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group not found", content = @Content)
    })
    public ResponseEntity<List<GroupGoalsResponseDto>> getGoalsByGroupId(
            @Parameter(
                    description = "External UUID of the reading group",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupId
    ) {
        return ResponseEntity.ok(
                groupGoalsService.getGoalsByGroupId(groupId)
        );
    }

    @GetMapping("/group/{groupId}/active")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get active goal by reading group",
            description = "Retrieves the current ACTIVE goal of a reading group.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active group goal retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group or active goal not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> getActiveGoalByGroupId(
            @Parameter(
                    description = "External UUID of the reading group",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupId
    ) {
        return ResponseEntity.ok(
                groupGoalsService.getActiveGoalByGroupId(groupId)
        );
    }

    @PatchMapping("/{goalId}/status")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Change group goal status",
            description = """
                    Changes the status of an ACTIVE group goal.
                    Allowed transitions:
                    ACTIVE -> COMPLETED
                    ACTIVE -> CANCELLED
                    Only the group creator, an admin, or a moderator can perform this action.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group goal status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status transition", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group goal not found", content = @Content)
    })
    public ResponseEntity<GroupGoalsResponseDto> changeStatus(
            @Parameter(
                    description = "External UUID of the group goal",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID goalId,
            @Valid @RequestBody GroupGoalStatusRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                groupGoalsService.changeStatus(goalId, requestDto)
        );
    }

    @DeleteMapping("/{goalId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Cancel an active group goal",
            description = """
                    Cancels an ACTIVE group goal.
                    The goal is not physically deleted.
                    Its status is changed to CANCELLED to preserve history.
                    Only the group creator, an admin, or a moderator can perform this action.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group goal cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Only ACTIVE goals can be cancelled", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group goal not found", content = @Content)
    })
    public ResponseEntity<Void> cancelGoal(
            @Parameter(
                    description = "External UUID of the group goal",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID goalId
    ) {
        groupGoalsService.cancelGoal(goalId);
        return ResponseEntity.noContent().build();
    }
}