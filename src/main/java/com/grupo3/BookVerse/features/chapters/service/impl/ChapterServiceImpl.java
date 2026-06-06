package com.grupo3.BookVerse.features.chapters.service.impl;

import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.chapters.dto.ChapterRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;
import com.grupo3.BookVerse.features.chapters.mappers.ChapterMapper;
import com.grupo3.BookVerse.features.chapters.repository.ChapterRepository;
import com.grupo3.BookVerse.features.chapters.service.ChapterService;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.stories.repository.StoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    // Creates a new chapter after validating the story exists and the chapter number is unique within it.
    public ChapterResponseDto createChapter(ChapterRequestDto dto) {
        StoryEntity story = storyRepository.findByIdExternalAndIsDeletedFalse(dto.getStoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        if (chapterRepository.existsByStoryIdAndChapterNumber(story.getId(), dto.getChapterNumber())) {
            throw new DuplicateResourceException("A chapter with that number already exists for this story");
        }

        ChapterEntity chapter = chapterMapper.toEntity(dto);
        chapter.setIdExternal(UUID.randomUUID());
        chapter.setStory(story);

        ChapterEntity saved = chapterRepository.save(chapter);
        return chapterMapper.toResponseDto(saved);
    }

    @Override
    // Retrieves all non-deleted chapters ordered by creation date descending.
    @Transactional(readOnly = true)
    public List<ChapterResponseDto> getAllChapters() {
        return chapterRepository.findByIsDeletedFalseOrderByCreatedAtDesc().stream()
                .map(chapterMapper::toResponseDto)
                .toList();
    }

    @Override
    // Retrieves a chapter by its external ID and maps it to a response DTO.
    @Transactional(readOnly = true)
    public ChapterResponseDto getChapterByIdExternal(UUID idExternal) {
        ChapterEntity chapter = chapterRepository.findByIdExternalAndIsDeletedFalse(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));

        return chapterMapper.toResponseDto(chapter);
    }

    @Override
    // Retrieves all non-deleted chapters of a story ordered by chapter number ascending.
    @Transactional(readOnly = true)
    public List<ChapterResponseDto> getChaptersByStoryId(UUID storyId) {
        StoryEntity story = storyRepository.findByIdExternalAndIsDeletedFalse(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        return chapterRepository.findByStoryIdAndIsDeletedFalseOrderByChapterNumberAsc(story.getId()).stream()
                .map(chapterMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    // Updates an existing chapter's fields without changing its associated story.
    public ChapterResponseDto updateChapter(UUID idExternal, ChapterRequestDto dto) {
        ChapterEntity existing = chapterRepository.findByIdExternalAndIsDeletedFalse(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));

        existing.setChapterNumber(dto.getChapterNumber());
        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());
        existing.setPageCount(dto.getPageCount());
        existing.setPublished(dto.isPublished());
        existing.setHidden(dto.isHidden());

        ChapterEntity saved = chapterRepository.save(existing);
        return chapterMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    // Soft deletes a chapter by external ID after validating that it exists.
    public void deleteChapter(UUID idExternal) {
        ChapterEntity existing = chapterRepository.findByIdExternalAndIsDeletedFalse(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));

        existing.setDeleted(true);
        chapterRepository.save(existing);
    }
}
