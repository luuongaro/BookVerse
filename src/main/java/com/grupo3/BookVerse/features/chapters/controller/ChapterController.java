package com.grupo3.BookVerse.features.chapters.controller;

import com.grupo3.BookVerse.features.chapters.dto.ChapterRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;
import com.grupo3.BookVerse.features.chapters.service.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    public ChapterResponseDto createChapter(@Valid @RequestBody ChapterRequestDto dto) {
        return chapterService.createChapter(dto);
    }

    @GetMapping
    public List<ChapterResponseDto> getAllChapters() {
        return chapterService.getAllChapters();
    }

    @GetMapping("/{idExternal}")
    public ChapterResponseDto getChapterByIdExternal(@PathVariable UUID idExternal) {
        return chapterService.getChapterByIdExternal(idExternal);
    }

    @GetMapping("/story/{storyId}")
    public List<ChapterResponseDto> getChaptersByStoryId(@PathVariable UUID storyId) {
        return chapterService.getChaptersByStoryId(storyId);
    }

    @PutMapping("/{idExternal}")
    public ChapterResponseDto updateChapter(@PathVariable UUID idExternal,
                                            @Valid @RequestBody ChapterRequestDto dto) {
        return chapterService.updateChapter(idExternal, dto);
    }

    @DeleteMapping("/{idExternal}")
    public void deleteChapter(@PathVariable UUID idExternal) {
        chapterService.deleteChapter(idExternal);
    }
}
