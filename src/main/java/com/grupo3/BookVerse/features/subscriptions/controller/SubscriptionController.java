package com.grupo3.BookVerse.features.subscriptions.controller;

import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import com.grupo3.BookVerse.features.subscriptions.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(
        name = "Subscriptions",
        description = "Endpoints for retrieving subscription plans and upgrading the authenticated user to premium"
)
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all subscriptions",
            description = "Retrieves all subscription plans available in the system.",
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
    @PreAuthorize("isAuthenticated()")
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

    @PostMapping("/upgrade-to-premium")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Upgrade authenticated user to premium",
            description = "Simulates a successful subscription upgrade and assigns the PREMIUM plan to the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User upgraded to premium successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Premium subscription not found", content = @Content)
    })
    public ResponseEntity<SubscriptionResponseDto> upgradeToPremium() {
        return ResponseEntity.ok(subscriptionService.upgradeAuthenticatedUserToPremium());
    }
}