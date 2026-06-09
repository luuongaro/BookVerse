package com.grupo3.BookVerse.features.reviews.controller;

import com.grupo3.BookVerse.features.reviews.dto.ReviewCreateRequestDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewResponseDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewUpdateRequestDto;
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
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(
        name = "Reviews",
        description = "Endpoints for managing reviews on stories and commercial books in BookVerse"
)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(
            summary = "Get all reviews",
            description = "Retrieves all active reviews in the system.",
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
            description = "Retrieves an active review using its external UUID identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    public ResponseEntity<ReviewResponseDto> getReviewById(
            @Parameter(
                    description = "External UUID of the review",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID reviewId
    ) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/book/{bookId}")
    @Operation(
            summary = "Get reviews by book",
            description = "Retrieves all active reviews associated with a specific commercial book.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book reviews retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByBookId(
            @Parameter(
                    description = "External UUID of the book",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID bookId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId));
    }

    @GetMapping("/story/{storyId}")
    @Operation(
            summary = "Get reviews by story",
            description = "Retrieves all active reviews associated with a specific story.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Story reviews retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Story not found", content = @Content)
    })
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByStoryId(
            @Parameter(
                    description = "External UUID of the story",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID storyId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByStoryId(storyId));
    }

    @PostMapping
    @Operation(
            summary = "Create a new review",
            description = "Creates a new review for exactly one target: either a commercial book or a story.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or invalid review target", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reviewer, book, or story not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplicate review detected", content = @Content)
    })
    public ResponseEntity<ReviewResponseDto> createReview(
            @Valid @RequestBody ReviewCreateRequestDto reviewCreateRequestDto
    ) {
        return new ResponseEntity<>(
                reviewService.createReview(reviewCreateRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{reviewId}")
    @Operation(
            summary = "Update a review",
            description = "Updates an existing review. Reviewer and target association cannot be changed.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    public ResponseEntity<ReviewResponseDto> updateReview(
            @Parameter(
                    description = "External UUID of the review to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID reviewId,
            @Valid @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto
    ) {
        return ResponseEntity.ok(
                reviewService.updateReview(reviewId, reviewUpdateRequestDto)
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
    public ResponseEntity<Void> deleteReview(
            @Parameter(
                    description = "External UUID of the review to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID reviewId
    ) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}