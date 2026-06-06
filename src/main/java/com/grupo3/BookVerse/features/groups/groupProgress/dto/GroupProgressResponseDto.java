package com.grupo3.BookVerse.features.groups.groupProgress.dto;

import lombok.AllArgsConstructor;

import lombok.*;

        import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupProgressResponseDto {

    private UUID idExternal;

    private UUID groupId;
    private String groupName;

    private UUID userId;
    private String userName;
    private String userEmail;

    private Integer currentProgress;

    private LocalDateTime updatedAt;
}
