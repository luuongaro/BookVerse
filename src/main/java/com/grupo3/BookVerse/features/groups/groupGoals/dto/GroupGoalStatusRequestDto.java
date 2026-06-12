package com.grupo3.BookVerse.features.groups.groupGoals.dto;

import com.grupo3.BookVerse.features.groups.groupGoals.domain.GoalStatus;
import jakarta.validation.constraints.NotNull;

public record GroupGoalStatusRequestDto(

        @NotNull(message = "status cannot be null")
        GoalStatus status

) {
}