package com.grupo3.BookVerse.features.reviews.domain.mappers;

import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper
        extends IMapper<ReviewEntity, ReviewResponseDto> {

    @Mapping(source = "idExternal", target = "reviewId")
    @Mapping(source = "user.idExternal", target = "userId")
    ReviewResponseDto toDTO(ReviewEntity reviewEntity);

}