
package com.grupo3.BookVerse.features.authors.domain.mappers;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.authors.domain.dto.AuthorDTO;
import com.grupo3.BookVerse.common.model.IMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthorMapper extends IMapper<AuthorEntity, AuthorDTO> {

    AuthorDTO toDTO(AuthorEntity authorEntity);

    AuthorEntity toEntity(AuthorDTO authorDTO);
}
