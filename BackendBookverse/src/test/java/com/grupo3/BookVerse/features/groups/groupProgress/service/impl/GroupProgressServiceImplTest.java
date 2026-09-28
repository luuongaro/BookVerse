package com.grupo3.BookVerse.features.groups.groupProgress.service.impl;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GoalType;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.repository.GroupGoalsRepository;
import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberEntity;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.status.domain.ProgressType;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEnum;
import com.grupo3.BookVerse.features.status.repository.ReadingStatusRepository;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupProgressServiceImplTest {

    @Mock
    private ReadingGroupRepository readingGroupRepository;

    @Mock
    private GroupGoalsRepository groupGoalsRepository;

    @Mock
    private ReadingStatusRepository readingStatusRepository;

    @InjectMocks
    private GroupProgressServiceImpl groupProgressService;

    private UUID groupId;

    @BeforeEach
    void setUp() {
        groupId = UUID.randomUUID();
    }

    // Verify that progress is calculated correctly
    // when goal type is percentage
    @Test
    void calculateProgress_shouldReturnProgressDto() {

        ChapterEntity chapter1 = new ChapterEntity();
        ChapterEntity chapter2 = new ChapterEntity();
        ChapterEntity chapter3 = new ChapterEntity();
        ChapterEntity chapter4 = new ChapterEntity();

        StoryEntity story = new StoryEntity();
        UUID storyId = UUID.randomUUID();
        story.setIdExternal(storyId);
        story.setChapters(
                List.of(chapter1, chapter2, chapter3, chapter4)
        );

        ReadingGroupEntity group =
                new ReadingGroupEntity();

        group.setStory(story);

        GroupGoalsEntity goal =
                new GroupGoalsEntity();

        goal.setGoalType(GoalType.PERCENTAGE);
        goal.setTargetProgress(50);

        UserEntity user =
                new UserEntity();
        UUID userId = UUID.randomUUID();
        user.setIdExternal(userId);

        GroupMemberEntity member = new GroupMemberEntity();
        member.setUser(user);
        group.setMembers(List.of(member));

        ReadingStatusEntity status =
                new ReadingStatusEntity();

        status.setUser(user);
        status.setStatus(ReadingStatusEnum.IN_PROGRESS);
        status.setProgressType(ProgressType.CHAPTER);
        status.setProgressValue(2);

        when(readingGroupRepository.findByIdExternal(groupId))
                .thenReturn(Optional.of(group));

        when(groupGoalsRepository
                .findTopByGroup_IdExternalOrderByUpdatedAtDesc(groupId))
                .thenReturn(Optional.of(goal));

        when(readingStatusRepository.findByUserIdExternalInAndStoryIdExternal(
                List.of(userId),
                storyId
        )).thenReturn(List.of(status));

        GroupProgressResponseDto result =
                groupProgressService.calculateProgress(groupId);

        assertNotNull(result);

        assertEquals(
                GoalType.PERCENTAGE,
                result.goalType()
        );

        assertEquals(
                50.0,
                result.currentProgress()
        );

        assertEquals(
                50,
                result.targetProgress()
        );

        assertTrue(result.achieved());
    }

    // Verify that an exception is thrown
    // when the group does not exist
    @Test
    void calculateProgress_shouldThrowWhenGroupNotFound() {

        when(readingGroupRepository.findByIdExternal(groupId))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> groupProgressService.calculateProgress(groupId)
        );
    }

    // Verify that an exception is thrown
    // when no goal exists for the group
    @Test
    void calculateProgress_shouldThrowWhenGoalNotFound() {

        ReadingGroupEntity group =
                new ReadingGroupEntity();

        when(readingGroupRepository.findByIdExternal(groupId))
                .thenReturn(Optional.of(group));

        when(groupGoalsRepository
                .findTopByGroup_IdExternalOrderByUpdatedAtDesc(groupId))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> groupProgressService.calculateProgress(groupId)
        );
    }

    // Verify that progress is zero
    // when there are no valid reading statuses
    @Test
    void calculateProgress_shouldReturnZeroProgress() {

        ReadingGroupEntity group =
                new ReadingGroupEntity();

        GroupGoalsEntity goal =
                new GroupGoalsEntity();

        goal.setGoalType(GoalType.PERCENTAGE);
        goal.setTargetProgress(50);

        when(readingGroupRepository.findByIdExternal(groupId))
                .thenReturn(Optional.of(group));

        when(groupGoalsRepository
                .findTopByGroup_IdExternalOrderByUpdatedAtDesc(groupId))
                .thenReturn(Optional.of(goal));

        GroupProgressResponseDto result =
                groupProgressService.calculateProgress(groupId);

        assertNotNull(result);

        assertEquals(
                0.0,
                result.currentProgress()
        );

        assertFalse(result.achieved());
    }
}
