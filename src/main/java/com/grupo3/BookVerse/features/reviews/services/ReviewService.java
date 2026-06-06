package com.grupo3.BookVerse.features.reviews.services;

import com.grupo3.BookVerse.features.reviews.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewResponseDto;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<ReviewResponseDto> getAllReviews();

    ReviewResponseDto getReviewById(UUID reviewId);

    ReviewResponseDto save(ReviewRequestDto reviewRequestDto);

    void delete(UUID reviewId);
}