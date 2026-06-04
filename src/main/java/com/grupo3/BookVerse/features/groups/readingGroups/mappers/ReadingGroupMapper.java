package com.grupo3.BookVerse.features.groups.readingGroups.mappers;

import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupRequestDto;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupResponseDto;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReadingGroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "book", source = "bookId")
    @Mapping(target = "createdBy", source = "createdByUserId")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "progresses", ignore = true)
    @Mapping(target = "goals", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ReadingGroupEntity toEntity(ReadingGroupRequestDto dto);

    @Mapping(target = "bookId", source = "book.idExternal")
    @Mapping(target = "createdByUserId", source = "createdBy.idExternal")
    ReadingGroupResponseDto toResponseDto(ReadingGroupEntity entity);

    List<ReadingGroupResponseDto> toResponseDtoList(List<ReadingGroupEntity> entities);
    default BookEntity mapBook(UUID bookId) {
        if (bookId == null) {
            return null;
        }
        BookEntity book = new BookEntity();
        book.setIdExternal(bookId);
        return book;
    }

    default UserEntity mapUser(UUID userId) {
        if (userId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setIdExternal(userId);
        return user;
    }
}