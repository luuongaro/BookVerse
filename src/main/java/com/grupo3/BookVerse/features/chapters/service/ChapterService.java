package com.grupo3.BookVerse.features.chapters.service;


import com.grupo3.BookVerse.features.chapters.dto.ChapterRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;

import java.util.List;
import java.util.UUID;

public interface ChapterService {

    ChapterResponseDto createChapter(ChapterRequestDto chapterRequestDto);

    List<ChapterResponseDto> getAllChapters();

    ChapterResponseDto getChapterByIdExternal(UUID idExternal);

    List<ChapterResponseDto> getChaptersByStoryId(UUID storyId);

    ChapterResponseDto updateChapter(UUID idExternal,
                                     ChapterRequestDto chapterRequestDto);

    void deleteChapter(UUID idExternal);
}