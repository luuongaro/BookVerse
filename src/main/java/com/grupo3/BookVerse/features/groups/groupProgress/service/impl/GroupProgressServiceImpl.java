package com.grupo3.BookVerse.features.groups.groupProgress.service.impl;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.repository.GroupGoalsRepository;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;
import com.grupo3.BookVerse.features.groups.groupProgress.service.GroupProgressService;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.status.domain.ProgressType;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import com.grupo3.BookVerse.features.status.repository.ReadingStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupProgressServiceImpl implements GroupProgressService {

    private final ReadingGroupRepository readingGroupRepository;
    private final GroupGoalsRepository groupGoalsRepository;
    private final ReadingStatusRepository readingStatusRepository;

    @Override
    @Transactional(readOnly = true)
    public GroupProgressResponseDto calculateProgress(UUID groupId) {

        ReadingGroupEntity group = readingGroupRepository.findByIdExternal(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        GroupGoalsEntity goal =
                groupGoalsRepository.findTopByGroup_IdExternalOrderByUpdatedAtDesc(groupId)
                        .orElseThrow(() ->
                                new RuntimeException("Goal not found for group: " + groupId)
                        );

        List<ReadingStatusEntity> statuses = readingStatusRepository.findAll(); // simplificado


        statuses = statuses.stream()
                .filter(s -> s.getUser() != null)
                .toList();

        double total = 0;
        int count = 0;

        for (ReadingStatusEntity status : statuses) {

            Double normalized = normalize(status, group, goal);

            if (normalized != null) {
                total += normalized;
                count++;
            }
        }

        double avg = count == 0 ? 0 : total / count;

        boolean achieved = avg >= goal.getTargetProgress();

        return GroupProgressResponseDto.builder()
                .groupId(groupId)
                .goalType(goal.getGoalType())
                .currentProgress(avg)
                .targetProgress(goal.getTargetProgress())
                .achieved(achieved)
                .build();
    }

    private Double normalize(
            ReadingStatusEntity status,
            ReadingGroupEntity group,
            GroupGoalsEntity goal
    ) {

        if (status.getProgressType() == null || status.getProgressValue() == null) {
            return null;
        }

        ProgressType type = status.getProgressType();

        if (goal.getGoalType().name().equals("PERCENTAGE")) {

            if (type == ProgressType.PERCENTAGE) {
                return status.getProgressValue().doubleValue();
            }

            if (type == ProgressType.CHAPTER) {
                Integer total = getTotalChapters(group);
                return total == 0 ? 0 :
                        (status.getProgressValue() * 100.0) / total;
            }
        }

        if (goal.getGoalType().name().equals("CHAPTER")) {

            if (type == ProgressType.CHAPTER) {
                return status.getProgressValue().doubleValue();
            }

            if (type == ProgressType.PERCENTAGE) {
                Integer total = getTotalChapters(group);
                return (total * status.getProgressValue()) / 100.0;
            }
        }

        return null;
    }

    private Integer getTotalChapters(ReadingGroupEntity group) {

        if (group.getStory() == null) return 0;

        List<ChapterEntity> chapters = group.getStory().getChapters();

        return chapters.size();
    }
}