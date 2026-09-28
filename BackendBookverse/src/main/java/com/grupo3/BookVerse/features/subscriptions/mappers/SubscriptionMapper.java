package com.grupo3.BookVerse.features.subscriptions.mappers;

import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "type", expression = "java(entity.getType() != null ? entity.getType().name() : null)")
    SubscriptionResponseDto toResponseDto(SubscriptionEntity entity);

    List<SubscriptionResponseDto> toResponseDtoList(List<SubscriptionEntity> entities);
}