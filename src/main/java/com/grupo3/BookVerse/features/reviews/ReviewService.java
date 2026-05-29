package com.grupo3.BookVerse.features.reviews;

import com.grupo3.BookVerse.common.exceptions.EntityNotFoundException;
import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewResponseDto;
import com.grupo3.BookVerse.features.reviews.domain.mappers.ReviewRequestMapper;
import com.grupo3.BookVerse.features.reviews.repository.ReviewRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;

    private final IMapper<ReviewEntity, ReviewResponseDto> responseMapper;

    private final ReviewRequestMapper requestMapper;

    @Override
    public List<ReviewResponseDto> getAllReviews() {

        return reviewRepository.findAll()
                .stream()
                .map(responseMapper::toDTO)
                .toList();
    }

    @Override
    public ReviewResponseDto getReviewById(UUID reviewId) {

        return reviewRepository.findByIdExternal(reviewId)
                .map(responseMapper::toDTO)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Review",
                                "Review was not found",
                                "reviewId",
                                reviewId.toString()
                        ));
    }

    @Override
    public ReviewResponseDto save(ReviewRequestDto reviewRequestDto) {

        ReviewEntity toBeSaved = requestMapper.toEntity(reviewRequestDto);

        ReviewEntity saved = reviewRepository.save(toBeSaved);

        return responseMapper.toDTO(saved);
    }

    @Override
    public void delete(UUID reviewId) {

        ReviewEntity toBeDeleted = reviewRepository.findByIdExternal(reviewId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Review",
                                "Review was not found",
                                "reviewId",
                                reviewId.toString()
                        ));

        reviewRepository.delete(toBeDeleted);
    }
}