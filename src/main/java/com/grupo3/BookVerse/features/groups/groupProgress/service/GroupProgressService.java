package com.grupo3.BookVerse.features.groups.groupProgress.service;

import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressRequestDto;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;

import java.util.List;
import java.util.UUID;

public interface GroupProgressService {

    GroupProgressResponseDto createOrUpdateProgress(GroupProgressRequestDto requestDto);

    List<GroupProgressResponseDto> getAllProgress();

    GroupProgressResponseDto getProgressByIdExternal(UUID idExternal);

    List<GroupProgressResponseDto> getProgressByGroupId(UUID groupId);

    List<GroupProgressResponseDto> getProgressByUserId(UUID userId);

    void deleteProgress(UUID idExternal);
}