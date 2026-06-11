package com.grupo3.BookVerse.features.groups.groupGoals.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsResponseDto;
import com.grupo3.BookVerse.features.groups.groupGoals.mappers.GroupGoalsMapper;
import com.grupo3.BookVerse.features.groups.groupGoals.repository.GroupGoalsRepository;
import com.grupo3.BookVerse.features.groups.groupGoals.service.GroupGoalsService;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupGoalsServiceImpl implements GroupGoalsService {

    private final GroupGoalsRepository groupGoalsRepository;
    private final GroupGoalsMapper groupGoalsMapper;
    private final ReadingGroupRepository readingGroupRepository;

    @Override
    @Transactional
    public GroupGoalsResponseDto save(
            GroupGoalsRequestDto requestDto
    ) {

        ReadingGroupEntity group =
                findGroupByIdExternal(requestDto.groupId());

        GroupGoalsEntity entity =
                groupGoalsMapper.toEntity(requestDto);

        entity.setGroup(group);

        GroupGoalsEntity saved =
                groupGoalsRepository.save(entity);

        return groupGoalsMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public GroupGoalsResponseDto update(
            UUID groupGoalsId,
            GroupGoalsRequestDto requestDto
    ) {

        GroupGoalsEntity groupGoals =
                findByIdExternalEntity(groupGoalsId);

        ReadingGroupEntity group =
                findGroupByIdExternal(requestDto.groupId());

        groupGoals.setGroup(group);

        groupGoals.setGoalType(
                requestDto.goalType()
        );

        groupGoals.setTargetProgress(
                requestDto.targetProgress()
        );

        groupGoals.setTargetDate(
                requestDto.targetDate()
        );

        GroupGoalsEntity saved =
                groupGoalsRepository.save(groupGoals);

        return groupGoalsMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(UUID groupGoalsId) {

        GroupGoalsEntity groupGoals =
                findByIdExternalEntity(groupGoalsId);

        groupGoalsRepository.delete(groupGoals);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupGoalsResponseDto findById(
            UUID groupGoalsId
    ) {

        GroupGoalsEntity groupGoals =
                findByIdExternalEntity(groupGoalsId);

        return groupGoalsMapper.toResponseDto(groupGoals);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupGoalsResponseDto> findAll() {

        return groupGoalsMapper.toResponseDtoList(
                groupGoalsRepository.findAll()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public GroupGoalsResponseDto findByGroupId(UUID groupId) {

        findGroupByIdExternal(groupId);

        GroupGoalsEntity groupGoal =
                groupGoalsRepository
                        .findTopByGroup_IdExternalOrderByUpdatedAtDesc(groupId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Group goal not found for group: " + groupId
                                )
                        );

        return groupGoalsMapper.toResponseDto(groupGoal);
    }

    private GroupGoalsEntity findByIdExternalEntity(
            UUID idExternal
    ) {

        return groupGoalsRepository
                .findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Group goal not found with idExternal: "
                                        + idExternal
                        )
                );
    }

    private ReadingGroupEntity findGroupByIdExternal(
            UUID groupId
    ) {

        return readingGroupRepository
                .findByIdExternal(groupId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reading group not found with idExternal: "
                                        + groupId
                        )
                );
    }
}