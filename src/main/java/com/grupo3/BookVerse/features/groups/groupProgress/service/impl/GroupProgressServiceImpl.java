package com.grupo3.BookVerse.features.groups.groupProgress.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.groups.groupProgress.domain.GroupProgressEntity;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressRequestDto;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;
import com.grupo3.BookVerse.features.groups.groupProgress.mapper.GroupProgressMapper;
import com.grupo3.BookVerse.features.groups.groupProgress.repository.GroupProgressRepository;
import com.grupo3.BookVerse.features.groups.groupProgress.service.GroupProgressService;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupProgressServiceImpl implements GroupProgressService {

    private final GroupProgressRepository groupProgressRepository;
    private final GroupProgressMapper groupProgressMapper;
    private final ReadingGroupRepository readingGroupRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GroupProgressResponseDto createOrUpdateProgress(GroupProgressRequestDto requestDto) {

        ReadingGroupEntity group = findGroupByExternalId(requestDto.getGroupId());
        UserEntity user = findUserByExternalId(requestDto.getUserId());

        GroupProgressEntity progress = groupProgressRepository
                .findByGroupIdAndUserId(group.getId(), user.getId())
                .orElseGet(() -> {
                    GroupProgressEntity newProgress = groupProgressMapper.toEntity(requestDto);
                    newProgress.setGroup(group);
                    newProgress.setUser(user);
                    return newProgress;
                });

        progress.setCurrentProgress(requestDto.getCurrentProgress());

        GroupProgressEntity saved = groupProgressRepository.save(progress);
        return groupProgressMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupProgressResponseDto> getAllProgress() {
        List<GroupProgressEntity> list =
                groupProgressRepository.findAllByOrderByUpdatedAtDesc();

        return list.stream()
                .map(groupProgressMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GroupProgressResponseDto getProgressByIdExternal(UUID idExternal) {
        GroupProgressEntity progress = findProgressByExternalId(idExternal);
        return groupProgressMapper.toResponseDto(progress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupProgressResponseDto> getProgressByGroupId(UUID groupId) {
        ReadingGroupEntity group = findGroupByExternalId(groupId);

        List<GroupProgressEntity> list =
                groupProgressRepository.findByGroupIdOrderByUpdatedAtDesc(group.getId());

        return list.stream()
                .map(groupProgressMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupProgressResponseDto> getProgressByUserId(UUID userId) {
        UserEntity user = findUserByExternalId(userId);

        List<GroupProgressEntity> list =
                groupProgressRepository.findByUserIdOrderByUpdatedAtDesc(user.getId());

        return list.stream()
                .map(groupProgressMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteProgress(UUID idExternal) {
        GroupProgressEntity progress = findProgressByExternalId(idExternal);
        groupProgressRepository.delete(progress);
    }

    private GroupProgressEntity findProgressByExternalId(UUID idExternal) {
        return groupProgressRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group progress not found with idExternal: " + idExternal));
    }

    private ReadingGroupEntity findGroupByExternalId(UUID groupExternalId) {
        return readingGroupRepository.findByIdExternal(groupExternalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group not found with idExternal: " + groupExternalId));
    }

    private UserEntity findUserByExternalId(UUID userExternalId) {
        return userRepository.findByIdExternal(userExternalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with idExternal: " + userExternalId));
    }
}