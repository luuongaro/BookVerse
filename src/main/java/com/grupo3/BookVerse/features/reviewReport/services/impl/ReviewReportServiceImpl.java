package com.grupo3.BookVerse.features.reviewReport.services.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportStatus;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;
import com.grupo3.BookVerse.features.reviewReport.mapper.ReviewReportMapper;
import com.grupo3.BookVerse.features.reviewReport.repository.ReviewReportRepository;
import com.grupo3.BookVerse.features.reviewReport.services.ReviewReportService;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.repository.ReviewRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewReportServiceImpl implements ReviewReportService {

    private final ReviewReportRepository reviewReportRepository;
    private final ReviewReportMapper reviewReportMapper;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewReportResponseDto> getAllReports() {

        List<ReviewReportEntity> reports =
                reviewReportRepository.findAll();

        return reports.stream()
                .map(reviewReportMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewReportResponseDto getReportById(UUID reportId) {

        ReviewReportEntity report =
                findReportByIdExternal(reportId);

        return reviewReportMapper.toResponseDto(report);
    }

    @Override
    @Transactional
    public ReviewReportResponseDto save(ReviewReportRequestDto dto) {

        ReviewEntity review =
                findReviewByIdExternal(dto.reviewId());

        UserEntity reporter =
                findUserByIdExternal(dto.reporterUserId());

        ReviewReportEntity report =
                reviewReportMapper.toEntity(dto);

        report.setReview(review);
        report.setReporterUser(reporter);
        report.setStatus(ReviewReportStatus.PENDING);

        ReviewReportEntity saved =
                reviewReportRepository.save(report);

        return reviewReportMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(UUID reportId) {

        ReviewReportEntity report =
                findReportByIdExternal(reportId);

        reviewReportRepository.delete(report);
    }

    private ReviewReportEntity findReportByIdExternal(UUID reportId) {

        return reviewReportRepository.findByIdExternal(reportId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review report not found with idExternal: " + reportId
                        )
                );
    }

    private ReviewEntity findReviewByIdExternal(UUID reviewId) {

        return reviewRepository.findByIdExternal(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with idExternal: " + reviewId
                        )
                );
    }

    private UserEntity findUserByIdExternal(UUID userId) {

        return userRepository.findByIdExternal(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: " + userId
                        )
                );
    }
}