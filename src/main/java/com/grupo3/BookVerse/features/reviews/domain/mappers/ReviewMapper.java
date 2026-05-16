
package com.grupo3.BookVerse.features.reviews.domain.mappers;
import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper
        extends IMapper<ReviewEntity, ReviewResponseDto> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    ReviewResponseDto toDTO(ReviewEntity reviewEntity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    ReviewEntity toEntity(ReviewResponseDto reviewDTO);
}