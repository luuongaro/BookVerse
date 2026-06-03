package com.grupo3.BookVerse.features.status.dto;

import com.grupo3.BookVerse.features.status.domain.ReadingStatusEnum;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReadingStatusRequestDto(

        @NotNull(message = "userId cannot be null")
        UUID userId,

        UUID bookId,

        UUID storyId,

        @NotNull(message = "status cannot be null")
        ReadingStatusEnum status,

        LocalDateTime startedAt,

        LocalDateTime finishedAt

) {
}
// bookId and storyId are optional because a reading status can be associated with:
// - a book only, a story only, both a book and a story
// Validation to ensure that at least one of them is provided is handled in the service layer.