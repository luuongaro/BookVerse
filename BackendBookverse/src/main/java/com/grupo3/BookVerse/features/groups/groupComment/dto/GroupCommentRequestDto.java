package com.grupo3.BookVerse.features.groups.groupComment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCommentRequestDto {


    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Progress percent is required")
    @Min(value = 0, message = "Progress percent must be at least 0")
    @Max(value = 100, message = "Progress percent must be at most 100")
    private Integer progressPercent;
}