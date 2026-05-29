package com.grupo3.BookVerse.features.reviews.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewReportRequestDto(

        @NotBlank(message = "reason cannot be empty")
        @Size(min = 5, max = 1000, message = "reason must be between 5 and 1000 characters")
        String reason

) {
}