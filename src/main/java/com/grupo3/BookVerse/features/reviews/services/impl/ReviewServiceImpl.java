package com.grupo3.BookVerse.features.reviews.services.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewResponseDto;
import com.grupo3.BookVerse.features.reviews.mapper.ReviewMapper;
import com.grupo3.BookVerse.features.reviews.repository.ReviewRepository;
import com.grupo3.BookVerse.features.reviews.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviews() {

        List<ReviewEntity> reviews =
                reviewRepository.findAll();

        return reviews.stream()
                .map(reviewMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(UUID reviewId) {

        ReviewEntity review =
                findReviewByIdExternal(reviewId);

        return reviewMapper.toResponseDto(review);
    }

    @Override
    @Transactional
    public ReviewResponseDto save(ReviewRequestDto reviewRequestDto) {

        ReviewEntity review =
                reviewMapper.toEntity(reviewRequestDto);

        ReviewEntity saved =
                reviewRepository.save(review);

        return reviewMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(UUID reviewId) {

        ReviewEntity review =
                findReviewByIdExternal(reviewId);

        reviewRepository.delete(review);
    }

    private ReviewEntity findReviewByIdExternal(UUID reviewId) {

        return reviewRepository.findByIdExternal(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with id: " + reviewId
                        )
                );
    }
}