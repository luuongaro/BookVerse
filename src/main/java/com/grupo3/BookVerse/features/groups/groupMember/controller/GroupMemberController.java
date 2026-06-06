package com.grupo3.BookVerse.features.groups.groupMember.controller;

import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberRequestDto;
import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberResponseDto;
import com.grupo3.BookVerse.features.groups.groupMember.service.GroupMemberService;
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
@RequestMapping("/api/group-members")
@RequiredArgsConstructor
@Tag(
        name = "Group Members",
        description = "Endpoints for managing membership in reading groups"
)
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    @PostMapping
    @Operation(
            summary = "Add a user to a reading group",
            description = "Creates a new group membership by associating a user with a reading group using their external UUIDs.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group member created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group or user not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "User is already a member of the group", content = @Content)
    })
    public ResponseEntity<GroupMemberResponseDto> createGroupMember(
            @Valid @RequestBody GroupMemberRequestDto groupMemberRequestDto
    ) {
        GroupMemberResponseDto createdGroupMember =
                groupMemberService.createGroupMember(groupMemberRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroupMember);
    }

    @GetMapping
    @Operation(
            summary = "Get all group memberships",
            description = "Retrieves all group membership records in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group members retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<GroupMemberResponseDto>> getAllGroupMembers() {
        List<GroupMemberResponseDto> groupMembers = groupMemberService.getAllGroupMembers();
        return ResponseEntity.ok(groupMembers);
    }

    @GetMapping("/{idExternal}")
    @Operation(
            summary = "Get group member by external id",
            description = "Retrieves a specific group membership by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group member retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group member not found", content = @Content)
    })
    public ResponseEntity<GroupMemberResponseDto> getGroupMemberByIdExternal(
            @Parameter(
                    description = "External UUID of the group membership",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        GroupMemberResponseDto groupMember =
                groupMemberService.getGroupMemberByIdExternal(idExternal);

        return ResponseEntity.ok(groupMember);
    }

    @GetMapping("/group/{groupId}")
    @Operation(
            summary = "Get group members by reading group",
            description = "Retrieves all members of a specific reading group using the group's external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group members retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reading group not found", content = @Content)
    })
    public ResponseEntity<List<GroupMemberResponseDto>> getGroupMembersByGroupId(
            @Parameter(
                    description = "External UUID of the reading group",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID groupId
    ) {
        List<GroupMemberResponseDto> groupMembers =
                groupMemberService.getGroupMembersByGroupId(groupId);

        return ResponseEntity.ok(groupMembers);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get group memberships by user",
            description = "Retrieves all group memberships associated with a specific user using the user's external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User group memberships retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<List<GroupMemberResponseDto>> getGroupMembersByUserId(
            @Parameter(
                    description = "External UUID of the user",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID userId
    ) {
        List<GroupMemberResponseDto> groupMembers =
                groupMemberService.getGroupMembersByUserId(userId);

        return ResponseEntity.ok(groupMembers);
    }

    @DeleteMapping("/{idExternal}")
    @Operation(
            summary = "Delete a group membership",
            description = "Deletes a group membership by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group member deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group member not found", content = @Content)
    })
    public ResponseEntity<Void> deleteGroupMember(
            @Parameter(
                    description = "External UUID of the group membership to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        groupMemberService.deleteGroupMember(idExternal);
        return ResponseEntity.noContent().build();
    }
}

