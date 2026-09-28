package com.grupo3.BookVerse.features.reviewReport.mapper;

import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportCreateRequestDto;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "review", ignore = true)
    @Mapping(target = "reporterUser", ignore = true)
    @Mapping(target = "moderatorUser", ignore = true)
    @Mapping(target = "resolutionComment", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    ReviewReportEntity toEntity(ReviewReportCreateRequestDto dto);

    @Mapping(source = "idExternal", target = "reportId")
    @Mapping(source = "review.idExternal", target = "reviewId")
    @Mapping(source = "reporterUser.idExternal", target = "reporterUserId")
    @Mapping(source = "moderatorUser.idExternal", target = "moderatorUserId")
    @Mapping(source = "status", target = "status")
    ReviewReportResponseDto toResponseDto(ReviewReportEntity report);

    List<ReviewReportResponseDto> toResponseListDto(List<ReviewReportEntity> reports);
}