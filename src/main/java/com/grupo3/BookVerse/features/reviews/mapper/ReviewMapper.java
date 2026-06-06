package com.grupo3.BookVerse.features.reviews.mapper;

import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.dto.ReviewRequestDto;
import com.grupo3.BookVerse.features.reviews.dto.ReviewResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "isHidden", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "takedownAt", ignore = true)
    @Mapping(target = "takedownReason", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "takedownStatus", ignore = true)
    ReviewEntity toEntity(ReviewRequestDto dto);

    @Mapping(source = "idExternal", target = "reviewId")
    @Mapping(source = "user.idExternal", target = "userId")
    @Mapping(source = "book.idExternal", target = "bookId")
    ReviewResponseDto toResponseDto(ReviewEntity review);

    List<ReviewResponseDto> toResponseListDto(List<ReviewEntity> reviews);
}
