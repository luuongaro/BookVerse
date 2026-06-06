package com.grupo3.BookVerse.features.groups.readingGroups.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReadingGroupRequestDto(

        @NotNull(message = "bookId cannot be null")
        UUID bookId,

        @NotNull(message = "createdByUserId cannot be null")
        UUID createdByUserId,

        @NotBlank(message = "name cannot be blank")
        String name,

        @NotNull(message = "isActive cannot be null")
        Boolean isActive

) {
}