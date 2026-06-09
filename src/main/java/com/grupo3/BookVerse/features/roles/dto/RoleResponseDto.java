package com.grupo3.BookVerse.features.roles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponseDto {

    private UUID idExternal;

    private String name;

    private Integer usersCount;

}

