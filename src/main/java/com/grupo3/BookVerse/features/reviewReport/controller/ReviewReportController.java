package com.grupo3.BookVerse.features.reviewReport.controller;


import com.grupo3.BookVerse.features.reviewReport.services.ReviewReportService;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review-reports")

@AllArgsConstructor
public class ReviewReportController {

    private final ReviewReportService reviewReportService;

    @GetMapping
    public ResponseEntity<List<ReviewReportResponseDto>> getAllReports() {

        return ResponseEntity.ok(reviewReportService.getAllReports());
    }

    @GetMapping("{reportId}")
    public ResponseEntity<ReviewReportResponseDto> findById(
            @PathVariable UUID reportId
    ) {

        return ResponseEntity.ok(
                reviewReportService.getReportById(reportId)
        );
    }

    @PostMapping
    public ResponseEntity<ReviewReportResponseDto> save(
            @RequestBody ReviewReportRequestDto reviewReportRequestDto
    ) {

        return ResponseEntity.ok(
                reviewReportService.save(reviewReportRequestDto)
        );
    }

    @DeleteMapping("{reportId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID reportId
    ) {

        reviewReportService.delete(reportId);

        return ResponseEntity.noContent().build();
    }
}