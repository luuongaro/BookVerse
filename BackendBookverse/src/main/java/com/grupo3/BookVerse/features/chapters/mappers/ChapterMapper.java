package com.grupo3.BookVerse.features.chapters.mappers;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.chapters.dto.ChapterCreateRequestDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterResponseDto;
import com.grupo3.BookVerse.features.chapters.dto.ChapterSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "story", ignore = true)
    @Mapping(target = "chapterNumber", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ChapterEntity toEntity(ChapterCreateRequestDto requestDto);

    @Mapping(target = "storyId", source = "story.idExternal")
    @Mapping(target = "storyTitle", source = "story.title")
    ChapterResponseDto toResponseDto(ChapterEntity entity);

    ChapterSummaryDto toSummaryDto(ChapterEntity entity);

    List<ChapterSummaryDto> toSummaryDtoList(List<ChapterEntity> chapters);
}