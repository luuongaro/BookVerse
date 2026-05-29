package com.grupo3.BookVerse.features.reviews.domain.mappers;

import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.reviews.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewReportRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewReportRequestMapper
        extends IMapper<ReviewReportEntity, ReviewReportRequestDto> {

    ReviewReportRequestDto toDTO(ReviewReportEntity reviewReportEntity);

    ReviewReportEntity toEntity(ReviewReportRequestDto reviewReportRequestDto);
}