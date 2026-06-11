package com.grupo3.BookVerse.features.groups.groupGoals.dto;

import com.grupo3.BookVerse.features.groups.groupGoals.domain.GoalType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GroupGoalsResponseDto(

        UUID idExternal,

        UUID groupId,

        GoalType goalType,

        Integer targetProgress,

        LocalDateTime targetDate,

        LocalDateTime updatedAt

) {
}