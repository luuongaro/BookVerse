package com.grupo3.BookVerse.features.stories.mappers;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.stories.dto.StoryRequestDto;
import com.grupo3.BookVerse.features.stories.dto.StoryResponseDto;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoryMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "author", source = "authorId")
    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "isHidden", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    StoryEntity toEntity(StoryRequestDto dto);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "chaptersCount", expression = "java(mapChaptersCount(storyEntity.getChapters()))")
    StoryResponseDto toResponseDto(StoryEntity storyEntity);

    List<StoryResponseDto> toResponseDtoList(List<StoryEntity> stories);

    default UserEntity map(Long authorId) {
        if (authorId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(authorId);
        return user;
    }

    default Integer mapChaptersCount(List<ChapterEntity> chapters) {
        return chapters != null ? chapters.size() : 0;
    }
}



