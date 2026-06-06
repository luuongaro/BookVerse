package com.grupo3.BookVerse.features.stories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryResponseDto {

    private UUID idExternal;
    private UUID authorId;
    private String title;
    private String description;
    private boolean isPrivate;
    private BigDecimal price;
    private boolean isHidden;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer chaptersCount;
}
