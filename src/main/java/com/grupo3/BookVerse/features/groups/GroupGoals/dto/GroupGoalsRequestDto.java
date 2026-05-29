package com.grupo3.BookVerse.features.groups.GroupGoals.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GroupGoalsRequestDto(

        @NotNull(message = "groupId cannot be null")
        @Positive(message = "groupId must be greater than 0")
        Long groupId,

        @NotNull(message = "targetProgress cannot be null")
        @Positive(message = "targetProgress must be greater than 0")
        Integer targetProgress,

        @NotNull(message = "targetDate cannot be null")
        @Future(message = "targetDate must be a future date")
        LocalDateTime targetDate,

        @NotNull(message = "averageProgress cannot be null")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "averageProgress must be greater than or equal to 0")
        BigDecimal averageProgress,

        Boolean achieved

) {
}