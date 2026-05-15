
package com.grupo3.BookVerse.features.reviews.domain.mappers;

import com.grupo3.BookVerse.common.model.IMapper;

import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NewReviewMapper
        extends IMapper<ReviewEntity, ReviewRequestDto> {

    @Override
    ReviewEntity toEntity(ReviewRequestDto newReviewDTO);

    @Override
    ReviewRequestDto toDTO(ReviewEntity reviewEntity);
}