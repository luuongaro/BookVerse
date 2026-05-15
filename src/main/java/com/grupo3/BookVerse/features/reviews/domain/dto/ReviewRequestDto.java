
package com.grupo3.BookVerse.features.reviews.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewRequestDto(

        @Min(value = 1, message = "rating must be at least 1")
        @Max(value = 5, message = "rating cannot exceed 5")
        Integer rating,

        @NotBlank(message = "content cannot be empty")
        @Size(min = 10, max = 5000, message = "content must be between 10 and 5000 characters")
        String content

) {
}