package com.grupo3.BookVerse.features.roles.service.impl;

import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.dto.RoleRequestDto;
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
    @Transactional
    public RoleResponseDto createRole(RoleRequestDto roleRequestDto) {

        String name =
                roleRequestDto.getName().trim().toUpperCase();

        if (roleRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException(
                    "Role already exists with name: " + name
            );
        }

        RoleEntity role =
                roleMapper.toEntity(roleRequestDto);

        role.setName(name);

        RoleEntity saved =
                roleRepository.save(role);

        return roleMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDto> getAllRoles() {

        List<RoleEntity> roles =
                roleRepository.findAll();

        return roleMapper.toResponseDtoList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDto getRoleByIdExternal(UUID idExternal) {

        RoleEntity role =
                findRoleByIdExternal(idExternal);

        return roleMapper.toResponseDto(role);
    }

    @Override
    @Transactional
    public RoleResponseDto updateRole(UUID idExternal, RoleRequestDto roleRequestDto) {

        RoleEntity existing =
                findRoleByIdExternal(idExternal);

        String name =
                roleRequestDto.getName().trim().toUpperCase();

        if (roleRepository.existsByNameIgnoreCase(name)
                && !existing.getName().equalsIgnoreCase(name)) {

            throw new DuplicateResourceException(
                    "Role already exists with name: " + name
            );
        }

        existing.setName(name);

        RoleEntity updated =
                roleRepository.save(existing);

        return roleMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteRole(UUID idExternal) {

        RoleEntity role =
                findRoleByIdExternal(idExternal);

        roleRepository.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDto getRoleByName(String name) {

        RoleEntity role =
                roleRepository.findByNameIgnoreCase(name)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found with name: " + name
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
}