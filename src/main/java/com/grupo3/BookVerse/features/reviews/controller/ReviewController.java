package com.grupo3.BookVerse.features.reviews.controller;

import com.grupo3.BookVerse.features.reviews.services.ReviewService;
import com.grupo3.BookVerse.features.reviews.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewResponseDto;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")

@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {

        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("{reviewId}")
    public ResponseEntity<ReviewResponseDto> findById(
            @PathVariable UUID reviewId
    ) {

        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> save(
            @RequestBody ReviewRequestDto reviewRequestDto
    ) {

        return ResponseEntity.ok(reviewService.save(reviewRequestDto));
    }

    @DeleteMapping("{reviewId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID reviewId
    ) {

        reviewService.delete(reviewId);

        return ResponseEntity.noContent().build();
    }
}