package com.grupo3.BookVerse.features.chapters.service;

import com.grupo3.BookVerse.features.chapters.dto.ChapterCreateRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterUpdateRequestDto;

import java.util.List;
import java.util.UUID;

public interface ChapterService {

    ChapterResponseDto createChapter(ChapterCreateRequestDto chapterCreateRequestDto);

    List<ChapterResponseDto> getAllChapters();

    ChapterResponseDto getChapterByIdExternal(UUID idExternal);

    List<ChapterResponseDto> getChaptersByStoryId(UUID storyId);

    ChapterResponseDto updateChapter(UUID idExternal, ChapterUpdateRequestDto chapterUpdateRequestDto);

    void deleteChapter(UUID idExternal);
}