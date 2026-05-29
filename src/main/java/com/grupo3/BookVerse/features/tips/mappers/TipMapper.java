package com.grupo3.BookVerse.features.tips.mappers;

import com.grupo3.BookVerse.features.tips.domain.TipEntity;
import com.grupo3.BookVerse.features.tips.dto.TipRequestDto;
import com.grupo3.BookVerse.features.tips.dto.TipResponseDto;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idExternal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "sender", source = "senderUserId")
    @Mapping(target = "receiver", source = "receiverUserId")
    TipEntity toEntity(TipRequestDto dto);

    @Mapping(target = "senderUserId", source = "sender.id")
    @Mapping(target = "receiverUserId", source = "receiver.id")
    TipResponseDto toResponseDto(TipEntity tipEntity);

    List<TipResponseDto> toResponseDtoList(List<TipEntity> tips);

    // Helper method used by MapStruct to convert a user
    // ID into a UserEntity reference by setting only its id.
    default UserEntity map(Long userId) {
        if (userId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }
}