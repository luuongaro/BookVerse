package com.grupo3.BookVerse.features.subscriptions.controller;

import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionRequestDto;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import com.grupo3.BookVerse.features.subscriptions.service.SubscriptionService;
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
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(
        name = "Subscriptions",
        description = "Endpoints for managing subscription plans"
)
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(
            summary = "Create a new subscription",
            description = "Creates a new subscription plan with its corresponding limits, dates, and configuration.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<SubscriptionResponseDto> createSubscription(
            @Valid @RequestBody SubscriptionRequestDto dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subscriptionService.createSubscription(dto));
    }

    @GetMapping
    @Operation(
            summary = "Get all subscriptions",
            description = "Retrieves all subscription plans registered in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscriptions retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<SubscriptionResponseDto>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/{idExternal}")
    @Operation(
            summary = "Get subscription by external id",
            description = "Retrieves a specific subscription plan by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not found", content = @Content)
    })
    public ResponseEntity<SubscriptionResponseDto> getSubscriptionByIdExternal(
            @Parameter(
                    description = "External UUID of the subscription",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByIdExternal(idExternal));
    }

    @PutMapping("/{idExternal}")
    @Operation(
            summary = "Update a subscription",
            description = "Updates an existing subscription plan by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not found", content = @Content)
    })
    public ResponseEntity<SubscriptionResponseDto> updateSubscription(
            @Parameter(
                    description = "External UUID of the subscription to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal,
            @Valid @RequestBody SubscriptionRequestDto dto
    ) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(idExternal, dto));
    }

    @DeleteMapping("/{idExternal}")
    @Operation(
            summary = "Delete a subscription",
            description = "Deletes a subscription plan by its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subscription deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not found", content = @Content)
    })
    public ResponseEntity<Void> deleteSubscription(
            @Parameter(
                    description = "External UUID of the subscription to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID idExternal
    ) {
        subscriptionService.deleteSubscription(idExternal);
        return ResponseEntity.noContent().build();
    }
}
