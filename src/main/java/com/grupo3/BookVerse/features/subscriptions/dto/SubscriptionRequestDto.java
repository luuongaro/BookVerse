package com.grupo3.BookVerse.features.subscriptions.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionRequestDto {

    @NotBlank
    @Pattern(regexp = "FREE|PREMIUM", message = "Type must be FREE or PREMIUM")
    private String type;

    @NotNull
    @Min(0)
    private Integer maxStoriesPublished;

    private boolean advancedStatsEnabled;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;
}
