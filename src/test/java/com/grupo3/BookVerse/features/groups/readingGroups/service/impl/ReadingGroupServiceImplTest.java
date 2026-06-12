package com.grupo3.BookVerse.features.groups.readingGroups.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupRequestDto;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupResponseDto;
import com.grupo3.BookVerse.features.groups.readingGroups.mappers.ReadingGroupMapper;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.stories.repository.StoryRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadingGroupServiceImplTest {

    @Mock
    private ReadingGroupRepository repository;

    @Mock
    private ReadingGroupMapper mapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReadingGroupServiceImpl service;

    private UUID groupId;
    private UUID bookId;
    private UUID storyId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        groupId = UUID.randomUUID();
        bookId = UUID.randomUUID();
        storyId = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    // Verify that a reading group is created successfully with a book
    @Test
    void createGroup_shouldReturnResponseDto_whenBookProvided() {

        ReadingGroupRequestDto request =
                new ReadingGroupRequestDto(
                        bookId,
                        null,
                        userId,
                        "Fantasy Readers",
                        true
                );

        UserEntity user = new UserEntity();
        BookEntity book = new BookEntity();
        ReadingGroupEntity entity = new ReadingGroupEntity();
        ReadingGroupEntity saved = new ReadingGroupEntity();

        ReadingGroupResponseDto response =
                new ReadingGroupResponseDto(
                        groupId,
                        bookId,
                        null,
                        userId,
                        "Fantasy Readers",
                        true,
                        null
                );

        when(userRepository.findByIdExternal(userId))
                .thenReturn(Optional.of(user));

        when(bookRepository.findByIdExternal(bookId))
                .thenReturn(Optional.of(book));

        when(mapper.toEntity(request))
                .thenReturn(entity);

        when(repository.save(entity))
                .thenReturn(saved);

        when(mapper.toResponseDto(saved))
                .thenReturn(response);

        ReadingGroupResponseDto result =
                service.createGroup(request);

        assertNotNull(result);
        assertEquals(response, result);

        verify(repository).save(entity);
    }

    // Verify that a reading group is created successfully with a story
    @Test
    void createGroup_shouldReturnResponseDto_whenStoryProvided() {

        ReadingGroupRequestDto request =
                new ReadingGroupRequestDto(
                        null,
                        storyId,
                        userId,
                        "Manga Readers",
                        true
                );

        UserEntity user = new UserEntity();
        StoryEntity story = new StoryEntity();
        ReadingGroupEntity entity = new ReadingGroupEntity();
        ReadingGroupEntity saved = new ReadingGroupEntity();

        ReadingGroupResponseDto response =
                mock(ReadingGroupResponseDto.class);

        when(userRepository.findByIdExternal(userId))
                .thenReturn(Optional.of(user));

        when(storyRepository.findByIdExternal(storyId))
                .thenReturn(Optional.of(story));

        when(mapper.toEntity(request))
                .thenReturn(entity);

        when(repository.save(entity))
                .thenReturn(saved);

        when(mapper.toResponseDto(saved))
                .thenReturn(response);

        ReadingGroupResponseDto result =
                service.createGroup(request);

        assertEquals(response, result);
    }

    // Verify that an exception is thrown when both bookId and storyId are null
    @Test
    void createGroup_shouldThrowException_whenBookAndStoryAreNull() {

        ReadingGroupRequestDto request =
                new ReadingGroupRequestDto(
                        null,
                        null,
                        userId,
                        "Group",
                        true
                );

        assertThrows(
                BadRequestException.class,
                () -> service.createGroup(request)
        );
    }

    // Verify that an exception is thrown when both bookId and storyId are provided
    @Test
    void createGroup_shouldThrowException_whenBookAndStoryExist() {

        ReadingGroupRequestDto request =
                new ReadingGroupRequestDto(
                        bookId,
                        storyId,
                        userId,
                        "Group",
                        true
                );

        assertThrows(
                BadRequestException.class,
                () -> service.createGroup(request)
        );
    }

    // Verify that all reading groups are returned correctly
    @Test
    void getAllGroups_shouldReturnList() {

        List<ReadingGroupEntity> entities =
                List.of(new ReadingGroupEntity());

        List<ReadingGroupResponseDto> responses =
                List.of(mock(ReadingGroupResponseDto.class));

        when(repository.findAll())
                .thenReturn(entities);

        when(mapper.toResponseDtoList(entities))
                .thenReturn(responses);

        List<ReadingGroupResponseDto> result =
                service.getAllGroups();

        assertEquals(responses, result);

        verify(repository).findAll();
    }

    // Verify that a reading group is returned by external ID
    @Test
    void getGroupByIdExternal_shouldReturnDto() {

        ReadingGroupEntity entity =
                new ReadingGroupEntity();

        ReadingGroupResponseDto response =
                mock(ReadingGroupResponseDto.class);

        when(repository.findByIdExternal(groupId))
                .thenReturn(Optional.of(entity));

        when(mapper.toResponseDto(entity))
                .thenReturn(response);

        ReadingGroupResponseDto result =
                service.getGroupByIdExternal(groupId);

        assertEquals(response, result);
    }

    // Verify that an exception is thrown when the group is not found
    @Test
    void getGroupByIdExternal_shouldThrowException_whenNotFound() {

        when(repository.findByIdExternal(groupId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getGroupByIdExternal(groupId)
        );
    }

    // Verify that reading groups are returned by book ID
    @Test
    void getGroupsByBookIdExternal_shouldReturnList() {

        BookEntity book = new BookEntity();

        List<ReadingGroupEntity> entities =
                List.of(new ReadingGroupEntity());

        List<ReadingGroupResponseDto> responses =
                List.of(mock(ReadingGroupResponseDto.class));

        when(bookRepository.findByIdExternal(bookId))
                .thenReturn(Optional.of(book));

        when(repository.findByBook_IdExternal(bookId))
                .thenReturn(entities);

        when(mapper.toResponseDtoList(entities))
                .thenReturn(responses);

        List<ReadingGroupResponseDto> result =
                service.getGroupsByBookIdExternal(bookId);

        assertEquals(responses, result);
    }

    // Verify that reading groups are returned by user ID
    @Test
    void getGroupsByUserIdExternal_shouldReturnList() {

        UserEntity user = new UserEntity();

        List<ReadingGroupEntity> entities =
                List.of(new ReadingGroupEntity());

        List<ReadingGroupResponseDto> responses =
                List.of(mock(ReadingGroupResponseDto.class));

        when(userRepository.findByIdExternal(userId))
                .thenReturn(Optional.of(user));

        when(repository.findByCreatedBy_IdExternal(userId))
                .thenReturn(entities);

        when(mapper.toResponseDtoList(entities))
                .thenReturn(responses);

        List<ReadingGroupResponseDto> result =
                service.getGroupsByUserIdExternal(userId);

        assertEquals(responses, result);
    }

    // Verify that a reading group is updated successfully
    @Test
    void updateGroup_shouldReturnUpdatedDto() {

        ReadingGroupRequestDto request =
                new ReadingGroupRequestDto(
                        bookId,
                        null,
                        userId,
                        "Updated Group",
                        false
                );

        ReadingGroupEntity entity =
                new ReadingGroupEntity();

        UserEntity user =
                new UserEntity();

        BookEntity book =
                new BookEntity();

        ReadingGroupResponseDto response =
                mock(ReadingGroupResponseDto.class);

        when(repository.findByIdExternal(groupId))
                .thenReturn(Optional.of(entity));

        when(userRepository.findByIdExternal(userId))
                .thenReturn(Optional.of(user));

        when(bookRepository.findByIdExternal(bookId))
                .thenReturn(Optional.of(book));

        when(repository.save(entity))
                .thenReturn(entity);

        when(mapper.toResponseDto(entity))
                .thenReturn(response);

        ReadingGroupResponseDto result =
                service.updateGroup(groupId, request);

        assertEquals(response, result);

        verify(repository).save(entity);
    }

    // Verify that a reading group is deleted successfully
    @Test
    void deleteGroup_shouldCallRepository() {

        ReadingGroupEntity entity =
                new ReadingGroupEntity();

        when(repository.findByIdExternal(groupId))
                .thenReturn(Optional.of(entity));

        service.deleteGroup(groupId);

        verify(repository).delete(entity);
    }
}