package com.grupo3.BookVerse.features.users.domain.mappers;

import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.domain.dto.UserRequestDto;
import com.grupo3.BookVerse.features.users.domain.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "subscription", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "stories", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "tipsSent", ignore = true)
    @Mapping(target = "tipsReceived", ignore = true)
    @Mapping(target = "booksCreated", ignore = true)
    @Mapping(target = "groupsCreated", ignore = true)
    @Mapping(target = "groupMembers", ignore = true)
    @Mapping(target = "readingStatuses", ignore = true)
    UserEntity toEntityDto (UserRequestDto dto);

    @Mapping(target = "status", expression = "java(user.getStatus().name())")
    UserResponseDto toResponseDto (UserEntity user);

    List<UserResponseDto> toResponseListDto (List<UserEntity> users);
}
