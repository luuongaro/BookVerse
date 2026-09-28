package com.grupo3.BookVerse.features.chapters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterResponseDto {

    private UUID idExternal;
    private UUID storyId;
    private String storyTitle;
    private int chapterNumber;
    private String title;
    private String content;
    private boolean published;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}