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
import com.grupo3.BookVerse.features.groups.readingGroups.service.ReadingGroupService;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.stories.repository.StoryRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadingGroupServiceImpl implements ReadingGroupService {

    private final ReadingGroupRepository repository;
    private final ReadingGroupMapper mapper;

    private final BookRepository bookRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReadingGroupResponseDto createGroup(
            ReadingGroupRequestDto dto
    ) {

        validateContent(
                dto.bookId(),
                dto.storyId()
        );

        UserEntity user =
                findUserByIdExternal(
                        dto.createdByUserId()
                );

        ReadingGroupEntity entity =
                mapper.toEntity(dto);

        entity.setCreatedBy(user);

        if (dto.bookId() != null) {

            entity.setBook(
                    findBookByIdExternal(
                            dto.bookId()
                    )
            );

            entity.setStory(null);
        }

        if (dto.storyId() != null) {

            entity.setStory(
                    findStoryByIdExternal(
                            dto.storyId()
                    )
            );

            entity.setBook(null);
        }

        ReadingGroupEntity saved =
                repository.save(entity);

        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getAllGroups() {

        return mapper.toResponseDtoList(
                repository.findAll()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ReadingGroupResponseDto getGroupByIdExternal(
            UUID idExternal
    ) {

        ReadingGroupEntity entity =
                findGroupByIdExternal(idExternal);

        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getGroupsByBookIdExternal(
            UUID bookId
    ) {

        findBookByIdExternal(bookId);

        return mapper.toResponseDtoList(
                repository.findByBook_IdExternal(bookId)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReadingGroupResponseDto> getGroupsByStoryIdExternal(
            UUID storyId
    ) {

        findStoryByIdExternal(storyId);

        return mapper.toResponseDtoList(
                repository.findByStory_IdExternal(storyId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getGroupsByUserIdExternal(
            UUID userId
    ) {

        findUserByIdExternal(userId);

        return mapper.toResponseDtoList(
                repository.findByCreatedBy_IdExternal(userId)
        );
    }

    @Override
    @Transactional
    public ReadingGroupResponseDto updateGroup(
            UUID idExternal,
            ReadingGroupRequestDto dto
    ) {

        validateContent(
                dto.bookId(),
                dto.storyId()
        );

        ReadingGroupEntity entity =
                findGroupByIdExternal(idExternal);

        UserEntity user =
                findUserByIdExternal(
                        dto.createdByUserId()
                );

        entity.setName(dto.name());
        entity.setIsActive(dto.isActive());
        entity.setCreatedBy(user);

        entity.setBook(null);
        entity.setStory(null);

        if (dto.bookId() != null) {

            entity.setBook(
                    findBookByIdExternal(
                            dto.bookId()
                    )
            );
        }

        if (dto.storyId() != null) {

            entity.setStory(
                    findStoryByIdExternal(
                            dto.storyId()
                    )
            );
        }

        ReadingGroupEntity updated =
                repository.save(entity);

        return mapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteGroup(UUID idExternal) {

        ReadingGroupEntity entity =
                findGroupByIdExternal(idExternal);

        repository.delete(entity);
    }

    private void validateContent(
            UUID bookId,
            UUID storyId
    ) {

        if (bookId == null && storyId == null) {

            throw new BadRequestException(
                    "A reading group must be associated with either a book or a story"
            );
        }

        if (bookId != null && storyId != null) {

            throw new BadRequestException(
                    "A reading group cannot be associated with both a book and a story"
            );
        }
    }

    private ReadingGroupEntity findGroupByIdExternal(
            UUID idExternal
    ) {

        return repository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reading group not found with idExternal: "
                                        + idExternal
                        ));
    }

    private BookEntity findBookByIdExternal(
            UUID idExternal
    ) {

        return bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: "
                                        + idExternal
                        ));
    }

    private StoryEntity findStoryByIdExternal(
            UUID idExternal
    ) {

        return storyRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Story not found with idExternal: "
                                        + idExternal
                        ));
    }

    private UserEntity findUserByIdExternal(
            UUID idExternal
    ) {

        return userRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: "
                                        + idExternal
                        ));
    }
}