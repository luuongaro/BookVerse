package com.grupo3.BookVerse.features.groups.GroupGoals.service;

import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;

import java.util.List;

public interface IGroupGoalsService {

    GroupGoalsResponseDto save(GroupGoalsRequestDto groupGoalsRequestDto);

    void delete(Long groupGoalsId);

    GroupGoalsResponseDto update(Long groupGoalsId, GroupGoalsRequestDto groupGoalsRequestDto);

    GroupGoalsResponseDto findById(Long groupGoalsId);

    List<GroupGoalsResponseDto> findAll();

    List<GroupGoalsResponseDto> findByGroupId(Long groupId);
}