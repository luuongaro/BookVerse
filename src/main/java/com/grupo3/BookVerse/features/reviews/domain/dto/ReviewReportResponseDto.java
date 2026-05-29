package com.grupo3.BookVerse.features.reviews.domain.dto;


import java.util.UUID;

public record ReviewReportResponseDto(

        UUID reportId,
        UUID reviewId,
        UUID reporterUserId,
        String reason,
        String status

) {
}