package com.grupo3.BookVerse.features.roles.mappers;

import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "usersCount", expression = "java(mapUsersCount(roleEntity))")
    RoleResponseDto toResponseDto(RoleEntity roleEntity);

    List<RoleResponseDto> toResponseDtoList(List<RoleEntity> roles);

    default Integer mapUsersCount(RoleEntity roleEntity) {
        return roleEntity.getUsers() != null ? roleEntity.getUsers().size() : 0;
    }
}