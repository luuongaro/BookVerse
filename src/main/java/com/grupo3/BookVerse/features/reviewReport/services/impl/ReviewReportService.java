package com.grupo3.BookVerse.features.reviewReport.services.impl;

import com.grupo3.BookVerse.common.exceptions.EntityNotFoundException;
import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;
import com.grupo3.BookVerse.features.reviewReport.mapper.ReviewReportMapper;
import com.grupo3.BookVerse.features.reviewReport.services.IReviewReportService;
import com.grupo3.BookVerse.features.reviewReport.repository.ReviewReportRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewReportService implements IReviewReportService {

    private final ReviewReportRepository reviewReportRepository;
    private final ReviewReportMapper reviewReportMapper;




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

        ReviewReportEntity toBeSaved = reviewReportMapper.toEntityDto(reviewReportRequestDto);

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