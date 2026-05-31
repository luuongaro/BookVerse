package com.grupo3.BookVerse.features.stories.service;

import com.grupo3.BookVerse.features.stories.dto.StoryRequestDto;
import com.grupo3.BookVerse.features.stories.dto.StoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface StoryService {

    StoryResponseDto createStory(StoryRequestDto storyRequestDto);

    List<StoryResponseDto> getAllStories();

    StoryResponseDto getStoryByIdExternal(UUID idExternal);

    List<StoryResponseDto> getStoriesByAuthorId(Long authorId);

    StoryResponseDto updateStory(UUID idExternal, StoryRequestDto storyRequestDto);

    void deleteStory(UUID idExternal);
}