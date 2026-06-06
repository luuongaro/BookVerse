package com.grupo3.BookVerse.features.books.mapper;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.dto.BookRequestDto;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookResponseDto> toResponseDtoList(List<BookEntity> bookEntities);

    @Mapping(target = "authorIds", expression = "java(mapAuthorIds(bookEntity.getAuthors()))")
    @Mapping(target = "authorNames", expression = "java(mapAuthorNames(bookEntity.getAuthors()))")
    @Mapping(target = "readingGroupsCount", expression = "java(mapReadingGroupsCount(bookEntity.getReadingGroups()))")
    @Mapping(target = "readingStatusesCount", expression = "java(mapReadingStatusesCount(bookEntity.getReadingStatuses()))")
    BookResponseDto toResponseDto(BookEntity bookEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "readingGroups", ignore = true)
    @Mapping(target = "readingStatuses", ignore = true)
    @Mapping(target = "authors", expression = "java(mapAuthors(bookRequestDto.getAuthorIds()))")
    BookEntity toEntity(BookRequestDto bookRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "readingGroups", ignore = true)
    @Mapping(target = "readingStatuses", ignore = true)
    @Mapping(target = "authors", expression = "java(mapAuthors(bookRequestDto.getAuthorIds()))")
    void updateEntityFromDto(BookRequestDto bookRequestDto, @MappingTarget BookEntity bookEntity);

    default Set<AuthorEntity> mapAuthors(Set<Long> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) {
            return Collections.emptySet();
        }

        return authorIds.stream()
                .map(this::mapAuthor)
                .collect(Collectors.toSet());
    }

    default AuthorEntity mapAuthor(Long authorId) {
        if (authorId == null) {
            return null;
        }

        AuthorEntity author = new AuthorEntity();
        author.setId(authorId);
        return author;
    }

    default Set<Long> mapAuthorIds(Set<AuthorEntity> authors) {
        if (authors == null || authors.isEmpty()) {
            return Collections.emptySet();
        }

        return authors.stream()
                .map(AuthorEntity::getId)
                .collect(Collectors.toSet());
    }

    default Set<String> mapAuthorNames(Set<AuthorEntity> authors) {
        if (authors == null || authors.isEmpty()) {
            return Collections.emptySet();
        }

        return authors.stream()
                .map(AuthorEntity::getFullName)
                .collect(Collectors.toSet());
    }

    default Integer mapReadingGroupsCount(List<ReadingGroupEntity> readingGroups) {
        return readingGroups != null ? readingGroups.size() : 0;
    }

    default Integer mapReadingStatusesCount(List<ReadingStatusEntity> readingStatuses) {
        return readingStatuses != null ? readingStatuses.size() : 0;
    }
}
