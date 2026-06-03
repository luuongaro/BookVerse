package com.grupo3.BookVerse.features.groups.GroupGoals.mappers;

import com.grupo3.BookVerse.features.groups.GroupGoals.domain.GroupGoalsEntity;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
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
    @Mapping(target = "group", source = "groupId")
    @Mapping(target = "updatedAt", ignore = true)
    GroupGoalsEntity toEntity(GroupGoalsRequestDto dto);

    @Mapping(target = "groupId", source = "group.idExternal")
    GroupGoalsResponseDto toResponseDto(GroupGoalsEntity entity);

    List<GroupGoalsResponseDto> toResponseDtoList(List<GroupGoalsEntity> entities);

    default ReadingGroupEntity map(Long groupId) {
        if (groupId == null) {
            return null;
        }
        ReadingGroupEntity group = new ReadingGroupEntity();
        group.setId(groupId);
        return group;
    }
}
