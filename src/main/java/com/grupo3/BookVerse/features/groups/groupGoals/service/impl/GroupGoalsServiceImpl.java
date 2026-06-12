package com.grupo3.BookVerse.features.groups.groupGoals.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GoalStatus;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalStatusRequestDto;
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

        validateNoActiveGoal(
                requestDto.groupId()
        );

        validateGoalParams(
                requestDto,
                group
        );

        GroupGoalsEntity entity =
                groupGoalsMapper.toEntity(requestDto);

        entity.setGroup(group);

        GroupGoalsEntity saved =
                groupGoalsRepository.save(entity);

        return groupGoalsMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public GroupGoalsResponseDto changeStatus(
            UUID groupGoalId,
            GroupGoalStatusRequestDto requestDto
    ) {

        GroupGoalsEntity groupGoal =
                findByIdExternalEntity(groupGoalId);

        validateStatusTransition(
                groupGoal,
                requestDto.status()
        );

        groupGoal.setStatus(
                requestDto.status()
        );

        GroupGoalsEntity saved =
                groupGoalsRepository.save(groupGoal);

        return groupGoalsMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(UUID groupGoalId) {

        GroupGoalsEntity groupGoal =
                findByIdExternalEntity(groupGoalId);

        if (groupGoal.getStatus() != GoalStatus.ACTIVE) {
            throw new BadRequestException(
                    "Only active goals can be cancelled"
            );
        }

        groupGoal.setStatus(
                GoalStatus.CANCELLED
        );

        groupGoalsRepository.save(groupGoal);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupGoalsResponseDto findById(
            UUID groupGoalId
    ) {

        GroupGoalsEntity groupGoal =
                findByIdExternalEntity(groupGoalId);

        return groupGoalsMapper.toResponseDto(groupGoal);
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
    public GroupGoalsResponseDto findByGroupId(
            UUID groupId
    ) {

        findGroupByIdExternal(groupId);

        GroupGoalsEntity groupGoal =
                groupGoalsRepository
                        .findByGroup_IdExternalAndStatus(
                                groupId,
                                GoalStatus.ACTIVE
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Active goal not found for group: "
                                                + groupId
                                )
                        );

        return groupGoalsMapper.toResponseDto(groupGoal);
    }

    private void validateGoalParams(
            GroupGoalsRequestDto requestDto,
            ReadingGroupEntity group
    ) {

        if (requestDto.targetProgress() == null || requestDto.targetProgress() <= 0) {
            throw new BadRequestException(
                    "Target progress must be greater than 0"
            );
        }

        if (
                requestDto.goalType().name().equals("PERCENTAGE")
                        && requestDto.targetProgress() > 100
        ) {
            throw new BadRequestException(
                    "Percentage target progress cannot exceed 100"
            );
        }

        if (
                group.getBook() != null
                        && requestDto.goalType().name().equals("CHAPTER")
        ) {
            throw new BadRequestException(
                    "Groups reading a book cannot set CHAPTER goals"
            );
        }
    }

    private void validateNoActiveGoal(
            UUID groupId
    ) {

        boolean hasActiveGoal =
                groupGoalsRepository
                        .existsByGroup_IdExternalAndStatus(
                                groupId,
                                GoalStatus.ACTIVE
                        );

        if (hasActiveGoal) {
            throw new BadRequestException(
                    "This reading group already has an active goal"
            );
        }
    }

    private void validateStatusTransition(
            GroupGoalsEntity goal,
            GoalStatus newStatus
    ) {

        if (goal.getStatus() != GoalStatus.ACTIVE) {
            throw new BadRequestException(
                    "Only active goals can change status"
            );
        }

        if (
                newStatus != GoalStatus.COMPLETED
                        && newStatus != GoalStatus.CANCELLED
        ) {
            throw new BadRequestException(
                    "Active goals can only become COMPLETED or CANCELLED"
            );
        }
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