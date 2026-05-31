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

    ReviewEntity toEntityDto(ReviewRequestDto dto);

    @Mapping(source = "idExternal", target = "reviewId")
    @Mapping(source = "user.idExternal", target = "userId")
    ReviewResponseDto toResponseDto(ReviewEntity review);

    List<ReviewResponseDto> toResponseListDto(List<ReviewEntity> reviews);
}