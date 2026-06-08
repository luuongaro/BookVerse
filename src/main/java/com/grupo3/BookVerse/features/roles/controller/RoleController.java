package com.grupo3.BookVerse.features.roles.controller;

import com.grupo3.BookVerse.features.roles.dto.RoleRequestDto;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;
import com.grupo3.BookVerse.features.roles.service.RoleService;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(
        name = "Roles",
        description = "Endpoints for managing roles in BookVerse"
)
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(
            summary = "Get all roles",
            description = "Retrieves a list of all registered roles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{idExternal}")
    @Operation(
            summary = "Get role by external id",
            description = "Retrieves a role using its external UUID identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    public ResponseEntity<RoleResponseDto> getRoleByIdExternal(
            @Parameter(
                    description = "External UUID of the role",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        return ResponseEntity.ok(roleService.getRoleByIdExternal(idExternal));
    }

    @PostMapping
    @Operation(
            summary = "Create a new role",
            description = "Creates a new role and returns the created resource.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<RoleResponseDto> createRole(
            @Valid @RequestBody RoleRequestDto roleRequestDto
    ) {
        return new ResponseEntity<>(
                roleService.createRole(roleRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{idExternal}")
    @Operation(
            summary = "Update a role",
            description = "Updates an existing role identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    public ResponseEntity<RoleResponseDto> updateRole(
            @Parameter(
                    description = "External UUID of the role to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal,
            @Valid @RequestBody RoleRequestDto roleRequestDto
    ) {
        return ResponseEntity.ok(
                roleService.updateRole(idExternal, roleRequestDto)
        );
    }

    @DeleteMapping("/{idExternal}")
    @Operation(
            summary = "Delete a role",
            description = "Deletes a role identified by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    public ResponseEntity<Void> deleteRole(
            @Parameter(
                    description = "External UUID of the role to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        roleService.deleteRole(idExternal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    @Operation(
            summary = "Get role by name",
            description = "Retrieves a role using its name.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    public ResponseEntity<RoleResponseDto> getRoleByName(
            @Parameter(
                    description = "Name of the role",
                    required = true,
                    example = "ADMIN"
            )
            @PathVariable String name
    ) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }
}
