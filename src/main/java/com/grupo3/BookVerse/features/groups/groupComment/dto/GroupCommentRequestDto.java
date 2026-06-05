package com.grupo3.BookVerse.features.groups.groupComment.dto;

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

    private Integer progressMilestone;
}