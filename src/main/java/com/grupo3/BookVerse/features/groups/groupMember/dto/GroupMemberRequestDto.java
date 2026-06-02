package com.grupo3.BookVerse.features.groups.groupMember.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMemberRequestDto {

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @NotNull(message = "User ID is required")
    private Long userId;
}