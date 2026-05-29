package com.grupo3.BookVerse.features.reviews;

import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewReportResponseDto;

import java.util.List;
import java.util.UUID;

public interface IReviewReportService {

    List<ReviewReportResponseDto> getAllReports();

    ReviewReportResponseDto getReportById(UUID reportId);

    ReviewReportResponseDto save(ReviewReportRequestDto reviewReportRequestDto);

    void delete(UUID reportId);
}