package com.grupo3.BookVerse.features.status.dto;

import com.grupo3.BookVerse.features.status.domain.ReadingStatusEnum;
import lombok.*;

        import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadingStatusResponseDto {

    private UUID idExternal;

    private UUID userId;

    private UUID bookId;

    private UUID storyId;

    private ReadingStatusEnum status;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private LocalDateTime updatedAt;
}