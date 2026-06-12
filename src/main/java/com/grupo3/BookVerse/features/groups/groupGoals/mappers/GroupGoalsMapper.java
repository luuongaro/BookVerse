package com.grupo3.BookVerse.features.groups.groupGoals.mappers;

import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.groupGoals.dto.GroupGoalsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GroupGoalsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    GroupGoalsEntity toEntity(GroupGoalsRequestDto dto);

    @Mapping(target = "groupId", source = "group.idExternal")
    @Mapping(target = "status", source = "status")


    GroupGoalsResponseDto toResponseDto(
            GroupGoalsEntity entity
    );

    List<GroupGoalsResponseDto> toResponseDtoList(
            List<GroupGoalsEntity> entities
    );
}