package com.grupo3.BookVerse.features.reviewReport.dtos;


import java.util.UUID;

public record ReviewReportResponseDto(

        Long reportId,
        Long reviewId,
        Long reporterUserId,
        String reason,
        String status

) {
}