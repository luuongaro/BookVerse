
package com.grupo3.BookVerse.features.reviews.domain.mappers;

import com.grupo3.BookVerse.common.model.IMapper;

import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.NewReviewDTO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NewReviewMapper
        extends IMapper<ReviewEntity, NewReviewDTO> {

    @Override
    ReviewEntity toEntity(NewReviewDTO newReviewDTO);

    @Override
    NewReviewDTO toDTO(ReviewEntity reviewEntity);
}