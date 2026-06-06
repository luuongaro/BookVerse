package com.grupo3.BookVerse.features.groups.groupProgress.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

        import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupProgressRequestDto {

    @NotNull(message = "Group ID is required")
    private UUID groupId;

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Current progress is required")
    @Min(value = 0, message = "Current progress cannot be negative")
    private Integer currentProgress;
}
