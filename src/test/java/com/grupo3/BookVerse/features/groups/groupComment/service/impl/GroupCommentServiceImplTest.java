package com.grupo3.BookVerse.features.groups.groupComment.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.groups.groupComment.domain.GroupCommentEntity;
import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentRequestDto;
import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentResponseDto;
import com.grupo3.BookVerse.features.groups.groupComment.mappers.GroupCommentMapper;
import com.grupo3.BookVerse.features.groups.groupComment.repository.GroupCommentRepository;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupCommentServiceImplTest {

    @Mock
    private GroupCommentRepository groupCommentRepository;

    @Mock
    private GroupCommentMapper groupCommentMapper;

    @Mock
    private ReadingGroupRepository readingGroupRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GroupCommentServiceImpl groupCommentService;

    // Verify that all comments are returned correctly
    @Test
    void shouldReturnAllComments() {
        GroupCommentEntity entity = GroupCommentEntity.builder()
                .content("Comentario 1")
                .hidden(false)
                .build();

        GroupCommentResponseDto dto = GroupCommentResponseDto.builder()
                .content("Comentario 1")
                .build();

        when(groupCommentRepository.findByHiddenFalseOrderByCreatedAtDesc())
                .thenReturn(List.of(entity));

        when(groupCommentMapper.toResponseDtoList(anyList()))
                .thenReturn(List.of(dto));

        List<GroupCommentResponseDto> result = groupCommentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Comentario 1", result.get(0).getContent());

        verify(groupCommentRepository).findByHiddenFalseOrderByCreatedAtDesc();
        verify(groupCommentMapper).toResponseDtoList(anyList());
    }

    // Verify that a comment is returned by external ID
    @Test
    void shouldReturnCommentByIdExternal() {
        UUID id = UUID.randomUUID();

        GroupCommentEntity entity = GroupCommentEntity.builder()
                .idExternal(id)
                .content("Comentario prueba")
                .hidden(false)
                .build();

        GroupCommentResponseDto dto = GroupCommentResponseDto.builder()
                .content("Comentario prueba")
                .build();

        when(groupCommentRepository.findByIdExternalAndHiddenFalse(id))
                .thenReturn(Optional.of(entity));

        when(groupCommentMapper.toResponseDto(entity))
                .thenReturn(dto);

        GroupCommentResponseDto result = groupCommentService.findById(id);

        assertNotNull(result);
        assertEquals("Comentario prueba", result.getContent());

        verify(groupCommentRepository).findByIdExternalAndHiddenFalse(id);
        verify(groupCommentMapper).toResponseDto(entity);
    }

    // Verify that an exception is thrown when the comment does not exist
    @Test
    void shouldThrowExceptionWhenCommentNotFound() {
        UUID id = UUID.randomUUID();

        when(groupCommentRepository.findByIdExternalAndHiddenFalse(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupCommentService.findById(id));

        verify(groupCommentRepository).findByIdExternalAndHiddenFalse(id);
    }

    // Verify that a comment is saved correctly
    @Test
    void shouldSaveCommentWhenGroupAndUserExist() {
        UUID groupId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        GroupCommentRequestDto requestDto = GroupCommentRequestDto.builder()
                .groupId(groupId)
                .userId(userId)
                .content("Nuevo comentario")
                .progressMilestone(20)
                .build();

        ReadingGroupEntity group = new ReadingGroupEntity();
        group.setIdExternal(groupId);

        UserEntity user = new UserEntity();
        user.setIdExternal(userId);

        GroupCommentEntity entity = new GroupCommentEntity();
        entity.setContent("Nuevo comentario");

        GroupCommentResponseDto dto = GroupCommentResponseDto.builder()
                .content("Nuevo comentario")
                .build();

        when(readingGroupRepository.findByIdExternal(groupId)).thenReturn(Optional.of(group));
        when(userRepository.findByIdExternal(userId)).thenReturn(Optional.of(user));
        when(groupCommentMapper.toEntity(requestDto)).thenReturn(entity);
        when(groupCommentRepository.save(entity)).thenReturn(entity);
        when(groupCommentMapper.toResponseDto(entity)).thenReturn(dto);

        GroupCommentResponseDto result = groupCommentService.save(requestDto);

        assertNotNull(result);
        assertEquals("Nuevo comentario", result.getContent());

        verify(groupCommentRepository).save(entity);
    }

    // Verify that delete marks comment as hidden
    @Test
    void shouldDeleteCommentByMarkingHidden() {
        UUID id = UUID.randomUUID();

        GroupCommentEntity entity = new GroupCommentEntity();
        entity.setIdExternal(id);
        entity.setHidden(false);

        when(groupCommentRepository.findByIdExternalAndHiddenFalse(id))
                .thenReturn(Optional.of(entity));

        groupCommentService.delete(id);

        assertTrue(entity.isHidden());
        verify(groupCommentRepository).save(entity);
    }
}
