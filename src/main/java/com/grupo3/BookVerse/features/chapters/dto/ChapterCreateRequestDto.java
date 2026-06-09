package com.grupo3.BookVerse.features.chapters.dto;

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
public class ChapterCreateRequestDto {

    @NotNull(message = "Story id is required")
    private UUID storyId;

    @NotBlank(message = "Chapter title is required")
    @Size(max = 150, message = "Chapter title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Chapter content is required")
    private String content;

    private boolean published;
}