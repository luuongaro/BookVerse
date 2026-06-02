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

    private Long id;
    private UUID idExternal;

    private Long groupId;
    private String groupName;

    private Long userId;
    private String userName;
    private String userEmail;

    private LocalDateTime joinedAt;
}