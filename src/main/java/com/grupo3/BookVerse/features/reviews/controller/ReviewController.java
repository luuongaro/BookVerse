package com.grupo3.BookVerse.features.reviews.controller;

import com.grupo3.BookVerse.features.reviews.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewResponseDto;
import com.grupo3.BookVerse.features.reviews.services.ReviewService;
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
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(
        name = "Reviews",
        description = "Endpoints for managing book reviews created by users"
)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(
            summary = "Get all reviews",
            description = "Retrieves all available reviews in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{reviewId}")
    @Operation(
            summary = "Get review by external id",
            description = "Retrieves a review using its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    public ResponseEntity<ReviewResponseDto> findById(
            @Parameter(
                    description = "External UUID of the review",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID reviewId
    ) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @PostMapping
    @Operation(
            summary = "Create a new review",
            description = "Creates a new review associated with a user and a book.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User or book not found", content = @Content)
    })
    public ResponseEntity<ReviewResponseDto> save(
            @Valid @RequestBody ReviewRequestDto reviewRequestDto
    ) {
        return new ResponseEntity<>(
                reviewService.save(reviewRequestDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{reviewId}")
    @Operation(
            summary = "Delete a review",
            description = "Performs a logical deletion of a review.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "External UUID of the review to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID reviewId
    ) {
        reviewService.delete(reviewId);

        return ResponseEntity.noContent().build();
    }
}


