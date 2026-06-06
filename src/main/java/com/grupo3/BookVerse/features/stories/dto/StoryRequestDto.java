package com.grupo3.BookVerse.features.stories.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryRequestDto {

    @NotNull(message = "Author id is required")
    private UUID authorId;

    @NotBlank(message = "Story title is required")
    @Size(max = 150, message = "Story title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Story description is required")
    @Size(max = 1000, message = "Story description must not exceed 1000 characters")
    private String description;

    private boolean isPrivate;

    @NotNull(message = "Story price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Story price must be greater than or equal to 0")
    private BigDecimal price;
}
