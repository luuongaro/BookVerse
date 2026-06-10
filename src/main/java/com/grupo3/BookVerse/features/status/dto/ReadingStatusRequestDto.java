package com.grupo3.BookVerse.features.status.dto;

import com.grupo3.BookVerse.features.status.domain.ProgressType;
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
        LocalDateTime finishedAt,

        ProgressType progressType,
        Integer progressValue

) {
}
