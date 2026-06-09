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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<ChapterResponseDto> getAllChapters(Pageable pageable) {
        Page<ChapterEntity> chapters =
                chapterRepository.findAllByDeletedFalseOrderByCreatedAtDesc(pageable);

        return chapters.map(chapterMapper::toResponseDto);
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
    public Page<ChapterResponseDto> getChaptersByStoryId(UUID storyId, Pageable pageable) {
        StoryEntity story = storyRepository.findByIdExternalAndDeletedFalse(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        Page<ChapterEntity> chapters =
                chapterRepository.findByStoryIdAndDeletedFalseOrderByChapterNumberAsc(story.getId(), pageable);

        return chapters.map(chapterMapper::toResponseDto);
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