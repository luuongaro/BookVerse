package com.grupo3.BookVerse.features.groups.groupProgress.mapper;

import com.grupo3.BookVerse.features.groups.groupProgress.domain.GroupProgressEntity;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressRequestDto;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupProgressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "user", ignore = true)
    GroupProgressEntity toEntity(GroupProgressRequestDto requestDto);

    @Mapping(target = "groupId", source = "group.idExternal")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "userId", source = "user.idExternal")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "userEmail", source = "user.email")
    GroupProgressResponseDto toResponseDto(GroupProgressEntity entity);

}


