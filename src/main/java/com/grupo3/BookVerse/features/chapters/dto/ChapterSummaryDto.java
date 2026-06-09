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
public class ChapterSummaryDto {

    private UUID idExternal;
    private Integer chapterNumber;
    private String title;
    private boolean published;
    private LocalDateTime createdAt;
}