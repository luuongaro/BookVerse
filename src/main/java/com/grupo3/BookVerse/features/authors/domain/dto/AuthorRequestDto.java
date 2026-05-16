package com.grupo3.BookVerse.features.authors.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorRequestDto(

        @NotBlank(message = "fullName cannot be empty")
        @Size(min = 2, max = 100, message = "fullName must be between 2 and 100 characters")
        String fullName,

        @NotBlank(message = "nationality cannot be empty")
        @Size(min = 2, max = 50, message = "nationality must be between 2 and 50 characters")
        String nationality,

        @NotBlank(message = "language cannot be empty")
        @Size(min = 2, max = 50, message = "language must be between 2 and 50 characters")
        String language

) {
}
