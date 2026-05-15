
package com.grupo3.BookVerse.features.authors.domain.mappers;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.authors.domain.dto.AuthorRequestDto;
import com.grupo3.BookVerse.common.model.IMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NewAuthorMapper extends IMapper<AuthorEntity, AuthorRequestDto> {

    AuthorEntity toEntity(AuthorRequestDto newAuthorDTO);

    AuthorRequestDto toDTO(AuthorEntity authorEntity);
}