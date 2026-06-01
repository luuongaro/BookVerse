package com.grupo3.BookVerse.features.reviewReport.dtos;


import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewReportResponseDto(

        UUID reportId,
        UUID reviewId,
        UUID reporterUserId,
        String reason,
        String status,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt

) {
}