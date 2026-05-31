package com.grupo3.BookVerse.features.groups.GroupGoals.service.impl;

import com.grupo3.BookVerse.common.EntityNotFoundException;

import com.grupo3.BookVerse.features.groups.GroupGoals.domain.GroupGoalsEntity;

import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.mappers.GroupGoalsMapper;
import com.grupo3.BookVerse.features.groups.GroupGoals.repository.GroupGoalsRepository;
import com.grupo3.BookVerse.features.groups.GroupGoals.service.IGroupGoalsService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupGoalsService implements IGroupGoalsService {

    private final GroupGoalsRepository groupGoalsRepository;
    private final GroupGoalsMapper groupGoalsMapper;


    @Override
    public GroupGoalsResponseDto save(GroupGoalsRequestDto groupGoalsRequestDto) {

        GroupGoalsEntity saved = groupGoalsRepository.save(
                groupGoalsMapper.toEntityDto(groupGoalsRequestDto)
        );

        return groupGoalsMapper.toResponseDto(saved);
    }

    @Override
    public void delete(Long groupGoalsId) {

        GroupGoalsEntity groupGoals = groupGoalsRepository.findById(groupGoalsId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "GroupGoals",
                        "Group goal was not found",
                        "groupGoalsId",
                        groupGoalsId.toString()
                ));

        groupGoalsRepository.delete(groupGoals);
    }

    @Override
    public GroupGoalsResponseDto update(Long groupGoalsId,
                                        GroupGoalsRequestDto groupGoalsRequestDto) {

        GroupGoalsEntity groupGoals = groupGoalsRepository.findById(groupGoalsId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "GroupGoals",
                        "Group goal was not found",
                        "groupGoalsId",
                        groupGoalsId.toString()
                ));

        groupGoals.setGroupId(groupGoalsRequestDto.groupId());
        groupGoals.setTargetProgress(groupGoalsRequestDto.targetProgress());
        groupGoals.setTargetDate(groupGoalsRequestDto.targetDate());
        groupGoals.setAverageProgress(groupGoalsRequestDto.averageProgress());
        groupGoals.setAchieved(groupGoalsRequestDto.achieved());

        GroupGoalsEntity saved = groupGoalsRepository.save(groupGoals);

        return groupGoalsMapper.toResponseDto(saved);
    }

    @Override
    public GroupGoalsResponseDto findById(Long groupGoalsId) {

        return groupGoalsRepository.findById(groupGoalsId)
                .map(groupGoalsMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "GroupGoals",
                        "Group goal was not found",
                        "groupGoalsId",
                        groupGoalsId.toString()
                ));
    }

    @Override
    public List<GroupGoalsResponseDto> findAll() {
        return groupGoalsRepository.findAll()
                .stream()
                .map(groupGoalsMapper::toResponseDto)
                .toList();


    }
}