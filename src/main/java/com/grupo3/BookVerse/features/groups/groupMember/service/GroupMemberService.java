package com.grupo3.BookVerse.features.groups.groupMember.service;

import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberRequestDto;
import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberResponseDto;

import java.util.List;
import java.util.UUID;

public interface GroupMemberService {

    GroupMemberResponseDto createGroupMember(GroupMemberRequestDto groupMemberRequestDto);

    List<GroupMemberResponseDto> getAllGroupMembers();

    GroupMemberResponseDto getGroupMemberByIdExternal(UUID idExternal);

    List<GroupMemberResponseDto> getGroupMembersByGroupId(UUID groupId);

    List<GroupMemberResponseDto> getGroupMembersByUserId(UUID userId);

    void deleteGroupMember(UUID idExternal);
}