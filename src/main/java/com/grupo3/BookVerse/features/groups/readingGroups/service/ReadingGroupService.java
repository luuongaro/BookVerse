package com.grupo3.BookVerse.features.groups.readingGroups.service;

import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupRequestDto;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupResponseDto;

import java.util.List;
import java.util.UUID;

public interface ReadingGroupService {

    ReadingGroupResponseDto createGroup(ReadingGroupRequestDto dto);

    List<ReadingGroupResponseDto> getAllGroups();

    ReadingGroupResponseDto getGroupByIdExternal(UUID idExternal);

    List<ReadingGroupResponseDto> getGroupsByBookId(UUID bookId);

    List<ReadingGroupResponseDto> getGroupsByUserId(UUID userId);

    ReadingGroupResponseDto updateGroup(UUID idExternal, ReadingGroupRequestDto dto);

    void deleteGroup(UUID idExternal);
}



