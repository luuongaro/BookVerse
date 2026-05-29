package com.grupo3.BookVerse.features.groups.GroupGoals.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GroupGoalsResponseDto(

        Long id,
        Long groupId,
        Integer targetProgress,
        LocalDateTime targetDate,
        BigDecimal averageProgress,
        Boolean achieved,
        LocalDateTime updatedAt

) {
}