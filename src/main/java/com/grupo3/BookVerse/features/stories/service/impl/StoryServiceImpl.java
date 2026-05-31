package com.grupo3.BookVerse.features.stories.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.stories.dto.StoryRequestDto;
import com.grupo3.BookVerse.features.stories.dto.StoryResponseDto;
import com.grupo3.BookVerse.features.stories.mappers.StoryMapper;
import com.grupo3.BookVerse.features.stories.repository.StoryRepository;
import com.grupo3.BookVerse.features.stories.service.StoryService;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public StoryResponseDto createStory(StoryRequestDto storyRequestDto) {
        UserEntity author = findUserById(storyRequestDto.getAuthorId());

        StoryEntity storyEntity = storyMapper.toEntity(storyRequestDto);
        storyEntity.setAuthor(author);

        StoryEntity savedStory = storyRepository.save(storyEntity);
        return storyMapper.toResponseDto(savedStory);
    }

    @Override
    public List<StoryResponseDto> getAllStories() {
        List<StoryEntity> stories = storyRepository.findByIsDeletedFalseOrderByCreatedAtDesc();
        return storyMapper.toResponseDtoList(stories);
    }

    @Override
    public StoryResponseDto getStoryByIdExternal(UUID idExternal) {
        StoryEntity storyEntity = findActiveStoryByIdExternal(idExternal);
        return storyMapper.toResponseDto(storyEntity);
    }

    @Override
    public List<StoryResponseDto> getStoriesByAuthorId(Long authorId) {
        findUserById(authorId);
        List<StoryEntity> stories = storyRepository.findByAuthorIdAndIsDeletedFalseOrderByCreatedAtDesc(authorId);
        return storyMapper.toResponseDtoList(stories);
    }

    @Override
    @Transactional
    public StoryResponseDto updateStory(UUID idExternal, StoryRequestDto storyRequestDto) {
        StoryEntity existingStory = findActiveStoryByIdExternal(idExternal);
        UserEntity author = findUserById(storyRequestDto.getAuthorId());

        existingStory.setTitle(storyRequestDto.getTitle());
        existingStory.setDescription(storyRequestDto.getDescription());
        existingStory.setPrivate(storyRequestDto.isPrivate());
        existingStory.setPrice(storyRequestDto.getPrice());
        existingStory.setAuthor(author);

        StoryEntity updatedStory = storyRepository.save(existingStory);
        return storyMapper.toResponseDto(updatedStory);
    }

    @Override
    @Transactional
    public void deleteStory(UUID idExternal) {
        StoryEntity storyEntity = findActiveStoryByIdExternal(idExternal);
        storyEntity.setDeleted(true);
        storyRepository.save(storyEntity);
    }

    private StoryEntity findActiveStoryByIdExternal(UUID idExternal) {
        return storyRepository.findByIdExternalAndIsDeletedFalse(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with idExternal: " + idExternal));
    }

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}


