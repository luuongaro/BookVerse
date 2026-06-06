package com.grupo3.BookVerse.features.groups.readingGroups.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupRequestDto;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupResponseDto;
import com.grupo3.BookVerse.features.groups.readingGroups.mappers.ReadingGroupMapper;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.groups.readingGroups.service.ReadingGroupService;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReadingGroupResponseDto createGroup(ReadingGroupRequestDto dto) {

        BookEntity book = findBookById(dto.bookId());
        UserEntity user = findUserById(dto.createdByUserId());

        ReadingGroupEntity entity = mapper.toEntity(dto);
        entity.setBook(book);
        entity.setCreatedBy(user);

        ReadingGroupEntity saved = repository.save(entity);
        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getAllGroups() {
        return mapper.toResponseDtoList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ReadingGroupResponseDto getGroupByIdExternal(UUID idExternal) {
        ReadingGroupEntity entity = findGroupByIdExternal(idExternal);
        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getGroupsByBookIdExternal(UUID bookId) {

        findBookById(bookId);

        List<ReadingGroupEntity> groups =
                repository.findByBook_IdExternal(bookId);

        return mapper.toResponseDtoList(groups);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingGroupResponseDto> getGroupsByUserIdExternal(UUID userId) {

        findUserById(userId);

        List<ReadingGroupEntity> groups =
                repository.findByCreatedBy_IdExternal(userId);

        return mapper.toResponseDtoList(groups);
    }

    @Override
    @Transactional
    public ReadingGroupResponseDto updateGroup(UUID idExternal, ReadingGroupRequestDto dto) {

        ReadingGroupEntity entity = findGroupByIdExternal(idExternal);

        BookEntity book = findBookById(dto.bookId());
        UserEntity user = findUserById(dto.createdByUserId());

        entity.setName(dto.name());
        entity.setIsActive(dto.isActive());
        entity.setBook(book);
        entity.setCreatedBy(user);

        ReadingGroupEntity updated = repository.save(entity);
        return mapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteGroup(UUID idExternal) {
        ReadingGroupEntity entity = findGroupByIdExternal(idExternal);
        repository.delete(entity);
    }

    private ReadingGroupEntity findGroupByIdExternal(UUID idExternal) {
        return repository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reading group not found with idExternal: " + idExternal
                        ));
    }

    private BookEntity findBookById(UUID idExternal) {
        return bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: " + idExternal
                        ));
    }

    private UserEntity findUserById(UUID idExternal) {
        return userRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: " + idExternal
                        ));
    }
}
