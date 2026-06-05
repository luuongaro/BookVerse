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

    private int chapterNumber;

    private String title;

    private String content;

    private int pageCount;

    private boolean isPublished;

    private boolean isHidden;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}