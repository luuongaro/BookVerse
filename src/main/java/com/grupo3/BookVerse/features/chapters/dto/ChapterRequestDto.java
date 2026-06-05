package com.grupo3.BookVerse.features.chapters.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterRequestDto {

    @NotNull(message = "Story id is required")
    private UUID storyId;

    @NotNull(message = "Chapter number is required")
    @Min(value = 1, message = "Chapter number must be greater than 0")
    private Integer chapterNumber;

    @NotBlank(message = "Chapter title is required")
    @Size(max = 150, message = "Chapter title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Chapter content is required")
    private String content;

    @NotNull(message = "Page count is required")
    @Min(value = 1, message = "Page count must be greater than 0")
    private Integer pageCount;

    private boolean isPublished;

    private boolean isHidden;
}