
package com.grupo3.BookVerse.features.roles.mappers;

import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.dto.RoleRequestDto;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "users", ignore = true)
    RoleEntity toEntity(RoleRequestDto dto);

    // Maps RoleEntity to RoleResponseDto and calculates the number of associated users.
    // Uses custom Java logic to calculate and map usersCount from roleEntity.
    @Mapping(target = "usersCount", expression = "java(mapUsersCount(roleEntity))")
    RoleResponseDto toResponseDto(RoleEntity roleEntity);

    List<RoleResponseDto> toResponseDtoList(List<RoleEntity> roles);

    default Integer mapUsersCount(RoleEntity roleEntity) {
        return roleEntity.getUsers() != null ? roleEntity.getUsers().size() : 0;
    }
}
