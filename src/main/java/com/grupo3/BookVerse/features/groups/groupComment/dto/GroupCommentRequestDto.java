package com.grupo3.BookVerse.features.groups.groupComment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

        import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCommentRequestDto {

    @NotNull(message = "Group id is required")
    private UUID groupId;

    @NotNull(message = "User id is required")
    private UUID userId;

    @NotBlank(message = "Content is required")
    private String content;

    @Min(value = 0, message = "Progress milestone must be at least 0")
    @Max(value = 100, message = "Progress milestone must be at most 100")
    private Integer progressMilestone;
}