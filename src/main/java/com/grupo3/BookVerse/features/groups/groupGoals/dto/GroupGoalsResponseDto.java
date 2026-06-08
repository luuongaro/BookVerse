package com.grupo3.BookVerse.features.groups.groupGoals.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GroupGoalsResponseDto(

        UUID idExternal,
        UUID groupId,
        Integer targetProgress,
        LocalDateTime targetDate,
        BigDecimal averageProgress,
        Boolean achieved,
        LocalDateTime updatedAt

) {
}