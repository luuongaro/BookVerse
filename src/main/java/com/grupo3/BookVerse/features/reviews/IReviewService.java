package com.grupo3.BookVerse.features.reviews;

import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewResponseDto;

import java.util.List;
import java.util.UUID;

public interface IReviewService {

    List<ReviewResponseDto> getAllReviews();

    ReviewResponseDto getReviewById(UUID reviewId);

    ReviewResponseDto save(ReviewRequestDto reviewRequestDto);

    void delete(UUID reviewId);
}