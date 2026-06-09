package com.grupo3.BookVerse.features.chapters.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.chapters.dto.ChapterCreateRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterUpdateRequestDto;
import com.grupo3.BookVerse.features.chapters.mappers.ChapterMapper;
import com.grupo3.BookVerse.features.chapters.repository.ChapterRepository;
import com.grupo3.BookVerse.features.chapters.service.ChapterService;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.stories.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;
    private final StoryRepository storyRepository;

    @Override
    @Transactional
    public ChapterResponseDto createChapter(ChapterCreateRequestDto dto) {

        StoryEntity story = storyRepository.findByIdExternalAndDeletedFalse(dto.getStoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        int nextChapterNumber = chapterRepository
                .findTopByStoryIdOrderByChapterNumberDesc(story.getId())
                .map(chapter -> chapter.getChapterNumber() + 1)
                .orElse(1);

        ChapterEntity chapter = chapterMapper.toEntity(dto);
        chapter.setStory(story);
        chapter.setChapterNumber(nextChapterNumber);

        ChapterEntity saved = chapterRepository.save(chapter);
        return chapterMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChapterResponseDto> getAllChapters() {
        return chapterRepository.findAllByDeletedFalseOrderByCreatedAtDesc().stream()
                .map(chapterMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ChapterResponseDto getChapterByIdExternal(UUID idExternal) {
        ChapterEntity chapter = chapterRepository.findByIdExternalAndDeletedFalse(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));

        validateParentStoryIsActive(chapter);

        return chapterMapper.toResponseDto(chapter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChapterResponseDto> getChaptersByStoryId(UUID storyId) {
        StoryEntity story = storyRepository.findByIdExternalAndDeletedFalse(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        return chapterRepository.findByStoryIdAndDeletedFalseOrderByChapterNumberAsc(story.getId()).stream()
                .map(chapterMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ChapterResponseDto updateChapter(UUID idExternal, ChapterUpdateRequestDto dto) {
        ChapterEntity existing = chapterRepository.findByIdExternalAndDeletedFalse(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));

        validateParentStoryIsActive(existing);

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());
        existing.setPublished(dto.isPublished());

        ChapterEntity saved = chapterRepository.save(existing);
        return chapterMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteChapter(UUID idExternal) {
        ChapterEntity existing = chapterRepository.findByIdExternalAndDeletedFalse(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));

        validateParentStoryIsActive(existing);

        existing.setDeleted(true);
        chapterRepository.save(existing);
    }

    private void validateParentStoryIsActive(ChapterEntity chapter) {
        StoryEntity story = chapter.getStory();

        if (story == null || story.isDeleted()) {
            throw new ResourceNotFoundException("Parent story not found or deleted");
        }
    }
}