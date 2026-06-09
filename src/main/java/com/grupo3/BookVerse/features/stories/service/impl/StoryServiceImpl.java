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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        UserEntity author = findUserByIdExternal(storyRequestDto.getAuthorId());

        StoryEntity storyEntity = storyMapper.toEntity(storyRequestDto);
        storyEntity.setAuthor(author);

        StoryEntity savedStory = storyRepository.save(storyEntity);

        return storyMapper.toResponseDto(savedStory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoryResponseDto> getAllStories() {

        List<StoryEntity> stories = storyRepository.findAllByDeletedFalseOrderByCreatedAtDesc();

        return storyMapper.toResponseDtoList(stories);
    }

    @Override
    @Transactional(readOnly = true)
    public StoryResponseDto getStoryByIdExternal(UUID idExternal) {

        StoryEntity story = findActiveStoryByIdExternal(idExternal);

        return storyMapper.toResponseDto(story);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoryResponseDto> getStoriesByAuthorId(UUID authorId) {

        UserEntity author = findUserByIdExternal(authorId);

        List<StoryEntity> stories =
                storyRepository.findByAuthorIdAndDeletedFalseOrderByCreatedAtDesc(author.getId());

        return storyMapper.toResponseDtoList(stories);
    }

    @Override
    @Transactional
    public StoryResponseDto updateStory(UUID idExternal, StoryRequestDto storyRequestDto) {

        StoryEntity existingStory = findActiveStoryByIdExternal(idExternal);
        UserEntity author = findUserByIdExternal(storyRequestDto.getAuthorId());

        existingStory.setTitle(storyRequestDto.getTitle());
        existingStory.setDescription(storyRequestDto.getDescription());
        existingStory.setAccessType(storyRequestDto.getAccessType());
        existingStory.setAuthor(author);

        StoryEntity updatedStory = storyRepository.save(existingStory);

        return storyMapper.toResponseDto(updatedStory);
    }

    @Override
    @Transactional
    public void deleteStory(UUID idExternal) {

        StoryEntity story = findActiveStoryByIdExternal(idExternal);
        story.setDeleted(true);

        storyRepository.save(story);
    }

    private StoryEntity findActiveStoryByIdExternal(UUID idExternal) {
        return storyRepository.findByIdExternalAndDeletedFalse(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Story not found with idExternal: " + idExternal
                        )
                );
    }

    private UserEntity findUserByIdExternal(UUID idExternal) {
        return userRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: " + idExternal
                        )
                );
    }
}