package com.grupo3.BookVerse.features.roles.service;

import com.grupo3.BookVerse.features.roles.dto.RoleRequestDto;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleResponseDto createRole(RoleRequestDto roleRequestDto);

    List<RoleResponseDto> getAllRoles();

    RoleResponseDto getRoleByIdExternal(UUID idExternal);

    RoleResponseDto updateRole(UUID idExternal, RoleRequestDto roleRequestDto);

    void deleteRole(UUID idExternal);

    RoleResponseDto getRoleByName(String name);
}
