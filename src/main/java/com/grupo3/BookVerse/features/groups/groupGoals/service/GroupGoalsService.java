package com.grupo3.BookVerse.features.groups.groupGoals.service;

import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalStatusRequestDto;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsResponseDto;

import java.util.List;
import java.util.UUID;

public interface GroupGoalsService {

    GroupGoalsResponseDto save(GroupGoalsRequestDto groupGoalsRequestDto);

    void delete(UUID groupGoalsId);

    GroupGoalsResponseDto changeStatus(
            UUID groupGoalId,
            GroupGoalStatusRequestDto requestDto
    );

    GroupGoalsResponseDto findById(UUID groupGoalsId);

    List<GroupGoalsResponseDto> findAll();

    GroupGoalsResponseDto findByGroupId(UUID groupId);
}
