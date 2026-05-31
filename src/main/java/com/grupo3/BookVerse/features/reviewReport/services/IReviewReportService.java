package com.grupo3.BookVerse.features.reviewReport.services;

import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;

import java.util.List;
import java.util.UUID;

public interface IReviewReportService {

    List<ReviewReportResponseDto> getAllReports();

    ReviewReportResponseDto getReportById(UUID reportId);

    ReviewReportResponseDto save(ReviewReportRequestDto reviewReportRequestDto);

    void delete(UUID reportId);
}