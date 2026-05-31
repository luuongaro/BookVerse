package com.grupo3.BookVerse.features.reviewReport.mapper;

import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewReportMapper {

    ReviewReportEntity toEntityDto(ReviewReportRequestDto dto);

    @Mapping(source = "id", target = "reportId")
    ReviewReportResponseDto toResponseDto(ReviewReportEntity report);

    List<ReviewReportResponseDto> toResponseListDto(List<ReviewReportEntity> reports);
}