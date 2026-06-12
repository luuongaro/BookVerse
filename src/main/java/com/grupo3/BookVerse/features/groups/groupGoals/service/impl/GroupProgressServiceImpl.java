package com.grupo3.BookVerse.features.groups.groupGoals.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.repository.GroupGoalsRepository;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;
import com.grupo3.BookVerse.features.groups.groupProgress.service.GroupProgressService;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.status.domain.ProgressType;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEnum;
import com.grupo3.BookVerse.features.status.repository.ReadingStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupProgressServiceImpl implements GroupProgressService {

    private static final Set<ReadingStatusEnum> VALID_PROGRESS_STATUSES = Set.of(
            ReadingStatusEnum.IN_PROGRESS,
            ReadingStatusEnum.RE_READING
    );

    private final ReadingGroupRepository readingGroupRepository;
    private final GroupGoalsRepository groupGoalsRepository;
    private final ReadingStatusRepository readingStatusRepository;

    @Override
    @Transactional(readOnly = true)
    public GroupProgressResponseDto calculateProgress(UUID groupId) {

        ReadingGroupEntity group = readingGroupRepository.findByIdExternal(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        GroupGoalsEntity goal = groupGoalsRepository.findTopByGroup_IdExternalOrderByUpdatedAtDesc(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found for group: " + groupId));

        List<UUID> memberIds = group.getMembers().stream()
                .map(member -> member.getUser().getIdExternal())
                .toList();

        if (memberIds.isEmpty()) {
            return buildResponse(groupId, goal, 0.0);
        }

        List<ReadingStatusEntity> statuses = getGroupReadingStatuses(group, memberIds);

        double totalProgress = 0;

        for (ReadingStatusEntity status : statuses) {
            if (status.getStatus() == ReadingStatusEnum.FINISHED) {
                totalProgress += goal.getTargetProgress().doubleValue();
            } else if (VALID_PROGRESS_STATUSES.contains(status.getStatus())) {
                Double normalized = normalize(status, group, goal);
                if (normalized != null) {
                    totalProgress += normalized;
                }
            }
        }

        double avg = totalProgress / memberIds.size();

        return buildResponse(groupId, goal, avg);
    }

    private List<ReadingStatusEntity> getGroupReadingStatuses(ReadingGroupEntity group, List<UUID> memberIds) {

        if (group.getBook() != null) {
            return readingStatusRepository.findByUserIdExternalInAndBookIdExternal(
                    memberIds,
                    group.getBook().getIdExternal()
            );
        }

        if (group.getStory() != null) {
            return readingStatusRepository.findByUserIdExternalInAndStoryIdExternal(
                    memberIds,
                    group.getStory().getIdExternal()
            );
        }

        throw new BadRequestException("Group must be associated with either a book or a story");
    }

    private Double normalize(
            ReadingStatusEntity status,
            ReadingGroupEntity group,
            GroupGoalsEntity goal
    ) {

        if (status.getProgressType() == null || status.getProgressValue() == null) {
            return 0.0;
        }

        ProgressType userProgressType = status.getProgressType();
        String goalType = goal.getGoalType().name();

        if (group.getBook() != null) {
            if (!userProgressType.name().equals(goalType)) {
                throw new BadRequestException(
                        "Cannot normalize progress for books. Progress type must match goal type."
                );
            }
            return status.getProgressValue().doubleValue();
        }

        if (goalType.equals("PERCENTAGE")) {
            if (userProgressType == ProgressType.PERCENTAGE) {
                return status.getProgressValue().doubleValue();
            }

            if (userProgressType == ProgressType.CHAPTER) {
                Integer total = getTotalChapters(group);
                return total == 0 ? 0.0 : (status.getProgressValue() * 100.0) / total;
            }
        }

        if (goalType.equals("CHAPTER")) {
            if (userProgressType == ProgressType.CHAPTER) {
                return status.getProgressValue().doubleValue();
            }

            if (userProgressType == ProgressType.PERCENTAGE) {
                Integer total = getTotalChapters(group);
                return (total * status.getProgressValue()) / 100.0;
            }
        }

        return 0.0;
    }

    private Integer getTotalChapters(ReadingGroupEntity group) {

        if (group.getStory() == null) {
            throw new BadRequestException("Cannot normalize chapters for books");
        }

        return group.getStory().getChapters().size();
    }

    private GroupProgressResponseDto buildResponse(UUID groupId, GroupGoalsEntity goal, double avg) {

        boolean achieved = avg >= goal.getTargetProgress();

        return GroupProgressResponseDto.builder()
                .groupId(groupId)
                .goalType(goal.getGoalType())
                .currentProgress(avg)
                .targetProgress(goal.getTargetProgress())
                .achieved(achieved)
                .build();
    }
}