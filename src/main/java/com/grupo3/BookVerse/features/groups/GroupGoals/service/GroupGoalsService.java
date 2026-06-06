package com.grupo3.BookVerse.features.groups.GroupGoals.service;

import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;

import java.util.List;
import java.util.UUID;

public interface GroupGoalsService {

    GroupGoalsResponseDto save(GroupGoalsRequestDto groupGoalsRequestDto);

    void delete(UUID groupGoalsId);

    GroupGoalsResponseDto update(UUID groupGoalsId, GroupGoalsRequestDto groupGoalsRequestDto);

    GroupGoalsResponseDto findById(UUID groupGoalsId);

    List<GroupGoalsResponseDto> findAll();

    List<GroupGoalsResponseDto> findByGroupId(UUID groupId);
}
