package com.grupo3.BookVerse.features.reviews.services.impl;


import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewResponseDto;
import com.grupo3.BookVerse.features.reviews.mapper.ReviewMapper;
import com.grupo3.BookVerse.features.reviews.repository.ReviewRepository;

import com.grupo3.BookVerse.features.reviews.services.ReviewService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewResponseDto> getAllReviews() {

        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toResponseDto)
                .toList();
    }

    @Override
    public ReviewResponseDto getReviewById(UUID reviewId) {

        return reviewRepository.findByIdExternal(reviewId)
                .map(reviewMapper::toResponseDto)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Review not found with id: " + reviewId
                                ));
    }

    @Override
    public ReviewResponseDto save(ReviewRequestDto reviewRequestDto) {

        ReviewEntity toBeSaved = reviewMapper.toEntity(reviewRequestDto);

        ReviewEntity saved = reviewRepository.save(toBeSaved);

        return reviewMapper.toResponseDto(saved);
    }

    @Override
    public void delete(UUID reviewId) {

        ReviewEntity toBeDeleted = reviewRepository.findByIdExternal(reviewId)
                .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Review not found with id: " + reviewId
                                ));

        reviewRepository.delete(toBeDeleted);
    }
}