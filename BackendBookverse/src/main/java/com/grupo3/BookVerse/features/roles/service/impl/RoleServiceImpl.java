package com.grupo3.BookVerse.features.roles.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;
import com.grupo3.BookVerse.features.roles.mappers.RoleMapper;
import com.grupo3.BookVerse.features.roles.repository.RoleRepository;
import com.grupo3.BookVerse.features.roles.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDto> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roleMapper.toResponseDtoList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDto getRoleByIdExternal(UUID idExternal) {
        RoleEntity role = findRoleByIdExternal(idExternal);
        return roleMapper.toResponseDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDto getRoleByName(String name) {
        String normalizedName = normalizeRoleName(name);

        RoleEntity role = roleRepository.findByNameIgnoreCase(normalizedName)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found with name: " + normalizedName
                        )
                );

        return roleMapper.toResponseDto(role);
    }

    private RoleEntity findRoleByIdExternal(UUID idExternal) {
        return roleRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found with idExternal: " + idExternal
                        )
                );
    }

    private String normalizeRoleName(String roleName) {
        String normalizedRoleName = roleName.trim().toUpperCase();

        if (!normalizedRoleName.startsWith("ROLE_")) {
            normalizedRoleName = "ROLE_" + normalizedRoleName;
        }

        return normalizedRoleName;
    }
}