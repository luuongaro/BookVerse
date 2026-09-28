package com.grupo3.BookVerse.features.reviewReport.dtos;

import jakarta.validation.constraints.Size;

public record ReviewReportModerationRequestDto(

        @Size(max = 3000, message = "Resolution comment must not exceed 3000 characters")
        String resolutionComment

) {}