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
        String name = roleRequestDto.getName().trim().toUpperCase();

        if (roleRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Role already exists with name: " + name);
        }

        RoleEntity roleEntity = roleMapper.toEntity(roleRequestDto);
        roleEntity.setName(name);

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
    public RoleResponseDto getRoleByIdExternal(UUID id) {
        RoleEntity roleEntity = roleRepository.findByIdExternal(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        return roleMapper.toResponseDto(roleEntity);
    }

    @Override
    @Transactional
    public RoleResponseDto updateRole(UUID id, RoleRequestDto roleRequestDto) {
        RoleEntity existingRole = roleRepository.findByIdExternal(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        String name = roleRequestDto.getName().trim().toUpperCase();

        if (roleRepository.existsByNameIgnoreCase(name)
                && !existingRole.getName().equalsIgnoreCase(name )) {
            throw new DuplicateResourceException("Role already exists with name: " + name );
        }

        existingRole.setName(name);

        RoleEntity updatedRole = roleRepository.save(existingRole);

        return roleMapper.toResponseDto(updatedRole);
    }

    @Override
    @Transactional
    public void deleteRole(UUID id) {
        RoleEntity roleEntity = roleRepository.findByIdExternal(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        roleRepository.delete(roleEntity);
    }

    @Override
    public RoleResponseDto getRoleByName(String name) {
        RoleEntity roleEntity = roleRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + name));

        return roleMapper.toResponseDto(roleEntity);
    }
}