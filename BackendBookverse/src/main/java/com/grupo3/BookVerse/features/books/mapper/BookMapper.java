package com.grupo3.BookVerse.features.books.mapper;

import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookResponseDto> toResponseDtoList(List<BookEntity> bookEntities);

    @Mapping(target = "readingGroupsCount", expression = "java(mapReadingGroupsCount(bookEntity.getReadingGroups()))")
    @Mapping(target = "readingStatusesCount", expression = "java(mapReadingStatusesCount(bookEntity.getReadingStatuses()))")
    BookResponseDto toResponseDto(BookEntity bookEntity);

    default Integer mapReadingGroupsCount(List<ReadingGroupEntity> readingGroups) {
        return readingGroups != null ? readingGroups.size() : 0;
    }

    default Integer mapReadingStatusesCount(List<ReadingStatusEntity> readingStatuses) {
        return readingStatuses != null ? readingStatuses.size() : 0;
    }
}