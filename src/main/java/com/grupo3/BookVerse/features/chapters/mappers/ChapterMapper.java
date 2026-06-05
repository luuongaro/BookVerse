package com.grupo3.BookVerse.features.chapters.mappers;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.chapters.dto.ChapterRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "story", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ChapterEntity toEntity(ChapterRequestDto requestDto);

    @Mapping(target = "storyId", source = "story.idExternal")
    @Mapping(target = "storyTitle", source = "story.title")
    ChapterResponseDto toResponseDto(ChapterEntity entity);

}