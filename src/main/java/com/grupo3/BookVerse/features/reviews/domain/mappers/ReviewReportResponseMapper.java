package com.grupo3.BookVerse.features.reviews.domain.mappers;

import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.reviews.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewReportResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewReportResponseMapper
        extends IMapper<ReviewReportEntity, ReviewReportResponseDto> {

    @Mapping(source = "id", target = "reportId")
    ReviewReportResponseDto toDTO(ReviewReportEntity reviewReportEntity);

}