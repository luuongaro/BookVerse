package com.grupo3.BookVerse.features.groups.groupComment.dto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCommentResponseDto {

    private UUID idExternal;

    private UUID groupId;

    private UUID userId;
    private String userName;

    private String content;

    private Integer progressPercent;

    private LocalDateTime createdAt;
}