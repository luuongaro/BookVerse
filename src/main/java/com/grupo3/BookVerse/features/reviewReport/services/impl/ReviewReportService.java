package com.grupo3.BookVerse.features.reviewReport.services.impl;

import com.grupo3.BookVerse.common.EntityNotFoundException;
import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;
import com.grupo3.BookVerse.features.reviewReport.mapper.ReviewReportMapper;
import com.grupo3.BookVerse.features.reviewReport.services.IReviewReportService;
import com.grupo3.BookVerse.features.reviewReport.repository.ReviewReportRepository;

import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.repository.ReviewRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewReportService implements IReviewReportService {

    private final ReviewReportRepository reviewReportRepository;
    private final ReviewReportMapper reviewReportMapper;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;





    @Override
    public List<ReviewReportResponseDto> getAllReports() {

        return reviewReportRepository.findAll()
                .stream()
                .map(reviewReportMapper::toResponseDto)
                .toList();
    }

    @Override
    public ReviewReportResponseDto getReportById(UUID reportId) {

        return reviewReportRepository.findByIdExternal(reportId)
                .map(reviewReportMapper::toResponseDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "ReviewReport",
                                "Review report was not found",
                                "reportId",
                                reportId.toString()
                        ));

    }

    @Override
    public ReviewReportResponseDto save(ReviewReportRequestDto reviewReportRequestDto) {

        ReviewReportEntity toBeSaved = reviewReportMapper.toEntity(reviewReportRequestDto);

        ReviewEntity review =
                reviewRepository.findByIdExternal(reviewReportRequestDto.reviewId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Review",
                                        "Review was not found",
                                        "reviewId",
                                        reviewReportRequestDto.reviewId().toString()
                                ));



        UserEntity reporter =
                userRepository.findByIdExternal(reviewReportRequestDto.reporterUserId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "User",
                                        "User was not found",
                                        "userId",
                                        reviewReportRequestDto.reporterUserId().toString()
                                ));

        toBeSaved.setReview(review);
        toBeSaved.setReporterUser(reporter);
        toBeSaved.setStatus(ReviewReportEntity.ReportStatus.PENDING);




        ReviewReportEntity saved = reviewReportRepository.save(toBeSaved);

        return reviewReportMapper.toResponseDto(saved);
    }

    @Override
    public void delete(UUID reportId) {

        ReviewReportEntity toBeDeleted =
                reviewReportRepository.findByIdExternal(reportId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "ReviewReport",
                                        "Review report was not found",
                                        "reportId",
                                        reportId.toString()
                                ));

        reviewReportRepository.delete(toBeDeleted);
    }
}