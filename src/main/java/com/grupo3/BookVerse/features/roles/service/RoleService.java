package com.grupo3.BookVerse.features.roles.service;

import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    List<RoleResponseDto> getAllRoles();

    RoleResponseDto getRoleByIdExternal(UUID idExternal);

    RoleResponseDto getRoleByName(String name);
}