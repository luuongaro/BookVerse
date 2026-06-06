package com.grupo3.BookVerse.features.subscriptions.mappers;

import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionType;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionRequestDto;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = SubscriptionType.class)
public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "type", expression = "java(dto.getType() != null ? SubscriptionType.valueOf(dto.getType()) : null)")
    SubscriptionEntity toEntity(SubscriptionRequestDto dto);

    @Mapping(target = "type", expression = "java(entity.getType() != null ? entity.getType().name() : null)")
    SubscriptionResponseDto toResponseDto(SubscriptionEntity entity);

    List<SubscriptionResponseDto> toResponseDtoList(List<SubscriptionEntity> entities);
}