package com.grupo3.BookVerse.features.roles.service.impl;

import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.dto.RoleRequestDto;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;
import com.grupo3.BookVerse.features.roles.mappers.RoleMapper;
import com.grupo3.BookVerse.features.roles.repository.RoleRepository;
import com.grupo3.BookVerse.features.roles.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        String normalizedName = roleRequestDto.getName().trim().toUpperCase();

        if (roleRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new DuplicateResourceException("Role already exists with name: " + normalizedName);
        }

        RoleEntity roleEntity = roleMapper.toEntity(roleRequestDto);
        roleEntity.setName(normalizedName);

        RoleEntity savedRole = roleRepository.save(roleEntity);

        return roleMapper.toResponseDto(savedRole);
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roleMapper.toResponseDtoList(roles);
    }

    @Override
    @Transactional
    public RoleResponseDto getRoleByIdExternal(UUID idExternal) {
        RoleEntity roleEntity = roleRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with idExternal: " + idExternal));

        return roleMapper.toResponseDto(roleEntity);
    }

    @Override
    @Transactional
    public RoleResponseDto updateRole(UUID idExternal, RoleRequestDto roleRequestDto) {
        RoleEntity existingRole = roleRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with idExternal: " + idExternal));

        String normalizedName = roleRequestDto.getName().trim().toUpperCase();

        if (roleRepository.existsByNameIgnoreCase(normalizedName)
                && !existingRole.getName().equalsIgnoreCase(normalizedName)) {
            throw new DuplicateResourceException("Role already exists with name: " + normalizedName);
        }

        existingRole.setName(normalizedName);

        RoleEntity updatedRole = roleRepository.save(existingRole);

        return roleMapper.toResponseDto(updatedRole);
    }

    @Override
    @Transactional
    public void deleteRole(UUID idExternal) {
        RoleEntity roleEntity = roleRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with idExternal: " + idExternal));

        roleRepository.delete(roleEntity);
    }

    @Override
    @Transactional
    public RoleResponseDto getRoleByName(String nameExternal) {
        RoleEntity roleEntity = roleRepository.findByNameIgnoreCase(nameExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + nameExternal));

        return roleMapper.toResponseDto(roleEntity);
    }
}