package com.grupo3.BookVerse.features.groups.GroupGoals.mappers;

import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.groups.GroupGoals.domain.GroupGoalsEntity;


import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NewGroupGoalsMapper extends IMapper<GroupGoalsEntity, GroupGoalsRequestDto> {

    GroupGoalsEntity toEntity(GroupGoalsRequestDto groupGoalsRequestDto);

    GroupGoalsRequestDto toDTO(GroupGoalsEntity groupGoalsEntity);
}