
package com.grupo3.BookVerse.features.authors.mapper;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.authors.dto.AuthorRequestDto;
import com.grupo3.BookVerse.features.authors.dto.AuthorResponseDto;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthorMapper {

    AuthorEntity toEntityDto(AuthorRequestDto dto);

    AuthorResponseDto toResponseDto(AuthorEntity author);

    List<AuthorResponseDto> toResponseListDto(List<AuthorEntity> authors);
}

