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
    ReviewReportEntity toEntity(ReviewReportRequestDto dto);

    @Mapping(source = "idExternal", target = "reportId")
    @Mapping(source = "review.idExternal", target = "reviewId")
    @Mapping(source = "reporterUser.idExternal", target = "reporterUserId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "resolvedAt", target = "resolvedAt")
    @Mapping(source = "status", target = "status")
    ReviewReportResponseDto toResponseDto(ReviewReportEntity report);


    List<ReviewReportResponseDto> toResponseListDto(List<ReviewReportEntity> reports);
}