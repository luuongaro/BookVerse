package com.grupo3.BookVerse.features.groups.groupProgress;

import com.grupo3.BookVerse.features.groups.groupGoals.domain.GoalType;

import java.util.UUID;


public record GroupProgressResponseDto(

        UUID groupId,

        GoalType goalType,

        Double currentProgress,

        Integer targetProgress,

        Boolean achieved

) {
}