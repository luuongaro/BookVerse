package com.grupo3.BookVerse.features.reviewReport.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReviewReportRequestDto(

        UUID reviewId,
        UUID reporterUserId,
        @NotBlank(message = "reason cannot be empty")
        @Size(min = 5, max = 1000, message = "reason must be between 5 and 1000 characters")
        String reason

) {
}