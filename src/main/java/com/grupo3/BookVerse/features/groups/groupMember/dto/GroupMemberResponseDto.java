package com.grupo3.BookVerse.features.groups.groupMember.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMemberResponseDto {

    private UUID idExternal;

    private UUID groupId;
    private String groupName;

    private UUID userId;
    private String userName;
    private String userEmail;

    private LocalDateTime joinedAt;
}