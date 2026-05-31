package com.grupo3.BookVerse.features.groups.GroupGoals.mappers;


import com.grupo3.BookVerse.features.groups.GroupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public interface GroupGoalsMapper {

    GroupGoalsEntity toEntityDto(GroupGoalsRequestDto dto);

    GroupGoalsResponseDto toResponseDto(GroupGoalsEntity entity);

    List<GroupGoalsResponseDto> toResponseListDto(List<GroupGoalsEntity> entities);
}