package com.grupo3.BookVerse.features.stories.controller;

import com.grupo3.BookVerse.features.stories.dto.StoryRequestDto;
import com.grupo3.BookVerse.features.stories.dto.StoryResponseDto;
import com.grupo3.BookVerse.features.stories.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<StoryResponseDto> createStory(@Valid @RequestBody StoryRequestDto storyRequestDto) {
        StoryResponseDto createdStory = storyService.createStory(storyRequestDto);
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StoryResponseDto>> getAllStories() {
        List<StoryResponseDto> stories = storyService.getAllStories();
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/{idExternal}")
    public ResponseEntity<StoryResponseDto> getStoryByIdExternal(@PathVariable UUID idExternal) {
        StoryResponseDto story = storyService.getStoryByIdExternal(idExternal);
        return ResponseEntity.ok(story);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<StoryResponseDto>> getStoriesByAuthorId(@PathVariable Long authorId) {
        List<StoryResponseDto> stories = storyService.getStoriesByAuthorId(authorId);
        return ResponseEntity.ok(stories);
    }

    @PutMapping("/{idExternal}")
    public ResponseEntity<StoryResponseDto> updateStory(
            @PathVariable UUID idExternal,
            @Valid @RequestBody StoryRequestDto storyRequestDto) {

        StoryResponseDto updatedStory = storyService.updateStory(idExternal, storyRequestDto);
        return ResponseEntity.ok(updatedStory);
    }

    @DeleteMapping("/{idExternal}")
    public ResponseEntity<Void> deleteStory(@PathVariable UUID idExternal) {
        storyService.deleteStory(idExternal);
        return ResponseEntity.noContent().build();
    }
}
