package com.grupo3.BookVerse.features.groups.groupGoals.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GoalStatus;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsResponseDto;
import com.grupo3.BookVerse.features.groups.groupGoals.mappers.GroupGoalsMapper;
import com.grupo3.BookVerse.features.groups.groupGoals.repository.GroupGoalsRepository;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupGoalsServiceImplTest {

    @Mock
    private GroupGoalsRepository groupGoalsRepository;

    @Mock
    private GroupGoalsMapper groupGoalsMapper;

    @Mock
    private ReadingGroupRepository readingGroupRepository;

    @InjectMocks
    private com.grupo3.BookVerse.features.groups.groupGoals.service.impl.GroupGoalsServiceImpl groupGoalsService;

    private UUID groupId;
    private UUID goalId;

    @BeforeEach
    void setUp() {
        groupId = UUID.randomUUID();
        goalId = UUID.randomUUID();
    }

    // Verifies that a group goal is saved correctly and the expected response DTO is returned
    @Test
    void save_shouldReturnResponseDto() {

        GroupGoalsRequestDto request = mock(GroupGoalsRequestDto.class);
        ReadingGroupEntity group = new ReadingGroupEntity();
        GroupGoalsEntity entity = new GroupGoalsEntity();
        GroupGoalsEntity saved = new GroupGoalsEntity();
        GroupGoalsResponseDto response = mock(GroupGoalsResponseDto.class);

        when(request.groupId()).thenReturn(groupId);

        when(readingGroupRepository.findByIdExternal(groupId))
                .thenReturn(Optional.of(group));

        when(groupGoalsMapper.toEntity(request)).thenReturn(entity);
        when(groupGoalsRepository.save(entity)).thenReturn(saved);
        when(groupGoalsMapper.toResponseDto(saved)).thenReturn(response);

        GroupGoalsResponseDto result = groupGoalsService.save(request);

        assertNotNull(result);
        assertEquals(response, result);

        verify(groupGoalsRepository).save(entity);
        verify(readingGroupRepository).findByIdExternal(groupId);
    }

    // Verifies that a group goal is returned correctly when searching by external ID
    @Test
    void findById_shouldReturnDto() {

        GroupGoalsEntity entity = new GroupGoalsEntity();
        GroupGoalsResponseDto response = mock(GroupGoalsResponseDto.class);

        when(groupGoalsRepository.findByIdExternal(goalId))
                .thenReturn(Optional.of(entity));

        when(groupGoalsMapper.toResponseDto(entity))
                .thenReturn(response);

        GroupGoalsResponseDto result = groupGoalsService.findById(goalId);

        assertEquals(response, result);

        verify(groupGoalsRepository).findByIdExternal(goalId);
    }

    // Verifies that an exception is thrown when the group goal does not exist
    @Test
    void findById_shouldThrowException_whenNotFound() {

        when(groupGoalsRepository.findByIdExternal(goalId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> groupGoalsService.findById(goalId));
    }

    // Verifies that an active group goal is cancelled when a valid external ID is provided
    @Test
    void delete_shouldCallRepository() {

        GroupGoalsEntity entity = new GroupGoalsEntity();
        entity.setStatus(GoalStatus.ACTIVE);

        when(groupGoalsRepository.findByIdExternal(goalId))
                .thenReturn(Optional.of(entity));

        groupGoalsService.delete(goalId);

        assertEquals(GoalStatus.CANCELLED, entity.getStatus());
        verify(groupGoalsRepository).save(entity);
        verify(groupGoalsRepository, never()).delete(entity);
    }

    @Test
    void delete_shouldThrow_whenGoalIsNotActive() {

        GroupGoalsEntity entity = new GroupGoalsEntity();
        entity.setStatus(GoalStatus.COMPLETED);

        when(groupGoalsRepository.findByIdExternal(goalId))
                .thenReturn(Optional.of(entity));

        assertThrows(BadRequestException.class,
                () -> groupGoalsService.delete(goalId));

        verify(groupGoalsRepository, never()).save(any());
    }

    // Verifies that all group goals are returned correctly and mapped to response DTOs
    @Test
    void findAll_shouldReturnList() {

        List<GroupGoalsEntity> entities = List.of(new GroupGoalsEntity());
        List<GroupGoalsResponseDto> dtos = List.of(mock(GroupGoalsResponseDto.class));

        when(groupGoalsRepository.findAll()).thenReturn(entities);
        when(groupGoalsMapper.toResponseDtoList(entities)).thenReturn(dtos);

        List<GroupGoalsResponseDto> result = groupGoalsService.findAll();

        assertEquals(dtos, result);
    }

    // Verifies that the latest group goal is returned when searching by reading group ID
    @Test
    void findByGroupId_shouldReturnLatestGoal() {

        GroupGoalsEntity entity = new GroupGoalsEntity();
        GroupGoalsResponseDto response = mock(GroupGoalsResponseDto.class);

        when(readingGroupRepository.findByIdExternal(groupId))
                .thenReturn(Optional.of(new ReadingGroupEntity()));

        when(groupGoalsRepository.findByGroup_IdExternalAndStatus(
                groupId,
                GoalStatus.ACTIVE
        )).thenReturn(Optional.of(entity));

        when(groupGoalsMapper.toResponseDto(entity))
                .thenReturn(response);

        GroupGoalsResponseDto result = groupGoalsService.findByGroupId(groupId);

        assertEquals(response, result);
    }

    // Verifies that an exception is thrown when no group goal exists for the specified group
    @Test
    void findByGroupId_shouldThrow_whenNoGoal() {

        when(readingGroupRepository.findByIdExternal(groupId))
                .thenReturn(Optional.of(new ReadingGroupEntity()));

        when(groupGoalsRepository.findByGroup_IdExternalAndStatus(
                groupId,
                GoalStatus.ACTIVE
        )).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> groupGoalsService.findByGroupId(groupId));
    }
}
