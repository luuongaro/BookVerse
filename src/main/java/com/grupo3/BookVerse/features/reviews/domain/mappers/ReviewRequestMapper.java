package com.grupo3.BookVerse.features.reviews.domain.mappers;


import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper
        extends IMapper<ReviewEntity, ReviewRequestDto> {

    ReviewRequestDto toDTO(ReviewEntity reviewEntity);

    ReviewEntity toEntity(ReviewRequestDto reviewRequestDto);
}