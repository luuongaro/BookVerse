package com.grupo3.BookVerse.features.subscriptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponseDto {

    private UUID idExternal;
    private String type;
    private int maxStoriesPublished;
    private int maxActiveStoriesReading;
    private boolean advancedStatsEnabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
