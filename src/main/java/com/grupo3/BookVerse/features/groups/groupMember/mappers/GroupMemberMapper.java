package com.grupo3.BookVerse.features.groups.groupMember.mappers;

import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberEntity;
import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberRequestDto;
import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "joinedAt", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "user", ignore = true)
    GroupMemberEntity toEntity(GroupMemberRequestDto requestDto);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "userEmail", source = "user.email")
    GroupMemberResponseDto toResponseDto(GroupMemberEntity entity);

}