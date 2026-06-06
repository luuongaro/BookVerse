package com.grupo3.BookVerse.features.tips.controller;

import com.grupo3.BookVerse.features.tips.dto.TipRequestDto;
import com.grupo3.BookVerse.features.tips.dto.TipResponseDto;
import com.grupo3.BookVerse.features.tips.service.TipService;
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
@RequestMapping("/api/tips")
@RequiredArgsConstructor
@Tag(
        name = "Tips",
        description = "Endpoints for managing tips between users"
)
public class TipController {

    private final TipService tipService;

    @PostMapping
    @Operation(
            summary = "Create a new tip",
            description = "Creates a new tip from one user to another using their external UUIDs.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tip created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or sender and receiver are the same", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sender or receiver user not found", content = @Content)
    })
    public ResponseEntity<TipResponseDto> createTip(
            @Valid @RequestBody TipRequestDto tipRequestDto
    ) {
        TipResponseDto createdTip = tipService.createTip(tipRequestDto);
        return new ResponseEntity<>(createdTip, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get all tips",
            description = "Retrieves all tips ordered by creation date descending.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tips retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<TipResponseDto>> getAllTips() {
        List<TipResponseDto> tips = tipService.getAllTips();
        return ResponseEntity.ok(tips);
    }

    @GetMapping("/{idExternal}")
    @Operation(
            summary = "Get tip by external id",
            description = "Retrieves a specific tip by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tip retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tip not found", content = @Content)
    })
    public ResponseEntity<TipResponseDto> getTipByIdExternal(
            @Parameter(
                    description = "External UUID of the tip",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        TipResponseDto tip = tipService.getTipByIdExternal(idExternal);
        return ResponseEntity.ok(tip);
    }

    @GetMapping("/sender/{senderId}")
    @Operation(
            summary = "Get tips by sender",
            description = "Retrieves all tips sent by a specific user using the sender's external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tips retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sender user not found", content = @Content)
    })
    public ResponseEntity<List<TipResponseDto>> getTipsBySenderId(
            @Parameter(
                    description = "External UUID of the sender user",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID senderId
    ) {
        List<TipResponseDto> tips = tipService.getTipsBySenderId(senderId);
        return ResponseEntity.ok(tips);
    }

    @GetMapping("/receiver/{receiverId}")
    @Operation(
            summary = "Get tips by receiver",
            description = "Retrieves all tips received by a specific user using the receiver's external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tips retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Receiver user not found", content = @Content)
    })
    public ResponseEntity<List<TipResponseDto>> getTipsByReceiverId(
            @Parameter(
                    description = "External UUID of the receiver user",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID receiverId
    ) {
        List<TipResponseDto> tips = tipService.getTipsByReceiverId(receiverId);
        return ResponseEntity.ok(tips);
    }

    @DeleteMapping("/{idExternal}")
    @Operation(
            summary = "Delete a tip",
            description = "Deletes a tip by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tip deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tip not found", content = @Content)
    })
    public ResponseEntity<Void> deleteTip(
            @Parameter(
                    description = "External UUID of the tip to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        tipService.deleteTip(idExternal);
        return ResponseEntity.noContent().build();
    }
}