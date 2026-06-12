package com.grupo3.BookVerse.features.groups.readingGroups.service.impl;

import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberEntity;
import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberStatus;
import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberType;
import com.grupo3.BookVerse.features.groups.groupMember.repository.GroupMemberRepository;
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
import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadingGroupServiceImpl implements ReadingGroupService {

    private final ReadingGroupRepository repository;
    private final ReadingGroupMapper mapper;

    private final BookRepository bookRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Override
    @Transactional
    public ReadingGroupResponseDto createGroup(ReadingGroupRequestDto dto) {
        validateContent(dto.bookId(), dto.storyId());

        UserEntity authenticatedUser = getAuthenticatedUser();

        ReadingGroupEntity entity = mapper.toEntity(dto);
        entity.setCreatedBy(authenticatedUser);
        entity.setIsActive(Boolean.TRUE);

        if (dto.bookId() != null) {
            entity.setBook(findBookByIdExternal(dto.bookId()));
            entity.setStory(null);
        }

        if (dto.storyId() != null) {
            entity.setStory(findStoryByIdExternal(dto.storyId()));
            entity.setBook(null);
        }

        ReadingGroupEntity saved = repository.save(entity);

        GroupMemberEntity creatorMembership = GroupMemberEntity.builder()
                .group(saved)
                .user(authenticatedUser)
                .memberType(GroupMemberType.CREATOR)
                .status(GroupMemberStatus.ACTIVE)
                .build();

        groupMemberRepository.save(creatorMembership);

        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getAllGroups() {
        List<ReadingGroupEntity> activeGroups = repository.findAll().stream()
                .filter(group -> Boolean.TRUE.equals(group.getIsActive()))
                .toList();

        return mapper.toResponseDtoList(activeGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public ReadingGroupResponseDto getGroupByIdExternal(UUID idExternal) {
        ReadingGroupEntity entity = findGroupByIdExternal(idExternal);

        if (!Boolean.TRUE.equals(entity.getIsActive())) {
            throw new ResourceNotFoundException(
                    "Reading group not found with idExternal: " + idExternal
            );
        }

        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getGroupsByBookIdExternal(UUID bookId) {
        findBookByIdExternal(bookId);

        List<ReadingGroupEntity> activeGroups = repository.findByBook_IdExternal(bookId).stream()
                .filter(group -> Boolean.TRUE.equals(group.getIsActive()))
                .toList();

        return mapper.toResponseDtoList(activeGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getGroupsByStoryIdExternal(UUID storyId) {
        findStoryByIdExternal(storyId);

        List<ReadingGroupEntity> activeGroups = repository.findByStory_IdExternal(storyId).stream()
                .filter(group -> Boolean.TRUE.equals(group.getIsActive()))
                .toList();

        return mapper.toResponseDtoList(activeGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getGroupsByUserIdExternal(UUID userId) {
        findUserByIdExternal(userId);

        List<ReadingGroupEntity> activeGroups = repository.findByCreatedBy_IdExternal(userId).stream()
                .filter(group -> Boolean.TRUE.equals(group.getIsActive()))
                .toList();

        return mapper.toResponseDtoList(activeGroups);
    }

    @Override
    @Transactional
    public ReadingGroupResponseDto updateGroup(UUID idExternal, ReadingGroupRequestDto dto) {
        validateContent(dto.bookId(), dto.storyId());

        ReadingGroupEntity entity = findGroupByIdExternal(idExternal);
        UserEntity authenticatedUser = getAuthenticatedUser();

        validateCanManageGroup(entity, authenticatedUser);

        if (!Boolean.TRUE.equals(entity.getIsActive())) {
            throw new BadRequestException("Inactive reading groups cannot be updated");
        }

        validateContentNotChanged(entity, dto);

        entity.setName(dto.name());
        entity.setIsActive(dto.isActive());

        ReadingGroupEntity updated = repository.save(entity);
        return mapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteGroup(UUID idExternal) {
        ReadingGroupEntity entity = findGroupByIdExternal(idExternal);
        UserEntity authenticatedUser = getAuthenticatedUser();

        validateCanManageGroup(entity, authenticatedUser);

        if (!Boolean.TRUE.equals(entity.getIsActive())) {
            throw new BadRequestException("Reading group is already inactive");
        }

        entity.setIsActive(false);
        repository.save(entity);
    }

    private void validateContent(UUID bookId, UUID storyId) {
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

    private void validateContentNotChanged(ReadingGroupEntity entity, ReadingGroupRequestDto dto) {
        boolean currentIsBookGroup = entity.getBook() != null;
        boolean currentIsStoryGroup = entity.getStory() != null;

        if (currentIsBookGroup) {
            boolean sameBook = dto.bookId() != null
                    && entity.getBook().getIdExternal().equals(dto.bookId());
            boolean storyMustBeNull = dto.storyId() == null;

            if (!sameBook || !storyMustBeNull) {
                throw new BadRequestException(
                        "Changing the associated content of a reading group is not allowed"
                );
            }
        }

        if (currentIsStoryGroup) {
            boolean sameStory = dto.storyId() != null
                    && entity.getStory().getIdExternal().equals(dto.storyId());
            boolean bookMustBeNull = dto.bookId() == null;

            if (!sameStory || !bookMustBeNull) {
                throw new BadRequestException(
                        "Changing the associated content of a reading group is not allowed"
                );
            }
        }
    }

    private void validateCanManageGroup(ReadingGroupEntity entity, UserEntity authenticatedUser) {
        boolean isOwner = entity.getCreatedBy() != null
                && Objects.equals(entity.getCreatedBy().getId(), authenticatedUser.getId());

        if (!isOwner && !isAdminOrModerator(authenticatedUser)) {
            throw new AccessDeniedException(
                    "You do not have permission to manage this reading group"
            );
        }
    }

    private boolean isAdminOrModerator(UserEntity user) {
        return user.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN")
                                || authority.getAuthority().equals("ROLE_MODERATOR"));
    }

    private ReadingGroupEntity findGroupByIdExternal(UUID idExternal) {
        return repository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reading group not found with idExternal: " + idExternal
                        ));
    }

    private BookEntity findBookByIdExternal(UUID idExternal) {
        return bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: " + idExternal
                        ));
    }

    private StoryEntity findStoryByIdExternal(UUID idExternal) {
        return storyRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Story not found with idExternal: " + idExternal
                        ));
    }

    private UserEntity findUserByIdExternal(UUID idExternal) {
        return userRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: " + idExternal
                        ));
    }

    private UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity user)) {
            throw new AccessDeniedException("Authenticated user not found");
        }

        return user;
    }
}

