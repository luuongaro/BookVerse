package com.grupo3.BookVerse.features.tips.mappers;

import com.grupo3.BookVerse.features.tips.domain.TipEntity;
import com.grupo3.BookVerse.features.tips.dto.TipRequestDto;
import com.grupo3.BookVerse.features.tips.dto.TipResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipMapper {

    // Maps only the simple fields from TipRequestDto to TipEntity.
    // Ignores auto-generated fields and relationships,
    // which are handled in the service layer.

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    TipEntity toEntity(TipRequestDto dto);

    @Mapping(target = "senderUserId", source = "sender.id")
    @Mapping(target = "receiverUserId", source = "receiver.id")
    TipResponseDto toResponseDto(TipEntity tipEntity);

    List<TipResponseDto> toResponseDtoList(List<TipEntity> tips);

}