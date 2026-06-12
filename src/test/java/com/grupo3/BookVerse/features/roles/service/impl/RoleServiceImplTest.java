package com.grupo3.BookVerse.features.roles.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;
import com.grupo3.BookVerse.features.roles.mappers.RoleMapper;
import com.grupo3.BookVerse.features.roles.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void shouldReturnAllRoles() {
        RoleEntity role =
                RoleEntity.builder()
                        .name("ROLE_USER")
                        .build();

        RoleResponseDto response =
                RoleResponseDto.builder()
                        .name("ROLE_USER")
                        .usersCount(0)
                        .build();

        when(roleRepository.findAll())
                .thenReturn(List.of(role));
        when(roleMapper.toResponseDtoList(List.of(role)))
                .thenReturn(List.of(response));

        List<RoleResponseDto> result =
                roleService.getAllRoles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ROLE_USER", result.get(0).getName());

        verify(roleRepository)
                .findAll();
        verify(roleMapper)
                .toResponseDtoList(List.of(role));
    }

    @Test
    void shouldReturnRoleByIdExternal() {
        UUID idExternal = UUID.randomUUID();

        RoleEntity role =
                RoleEntity.builder()
                        .idExternal(idExternal)
                        .name("ROLE_ADMIN")
                        .build();

        RoleResponseDto response =
                RoleResponseDto.builder()
                        .idExternal(idExternal)
                        .name("ROLE_ADMIN")
                        .usersCount(0)
                        .build();

        when(roleRepository.findByIdExternal(idExternal))
                .thenReturn(Optional.of(role));
        when(roleMapper.toResponseDto(role))
                .thenReturn(response);

        RoleResponseDto result =
                roleService.getRoleByIdExternal(idExternal);

        assertNotNull(result);
        assertEquals(idExternal, result.getIdExternal());
        assertEquals("ROLE_ADMIN", result.getName());

        verify(roleRepository)
                .findByIdExternal(idExternal);
        verify(roleMapper)
                .toResponseDto(role);
    }

    @Test
    void shouldThrowExceptionWhenRoleByIdExternalDoesNotExist() {
        UUID idExternal = UUID.randomUUID();

        when(roleRepository.findByIdExternal(idExternal))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> roleService.getRoleByIdExternal(idExternal)
        );

        verify(roleRepository)
                .findByIdExternal(idExternal);
        verify(roleMapper, never())
                .toResponseDto(any(RoleEntity.class));
    }

    @Test
    void shouldReturnRoleByNameNormalizingPrefixAndCase() {
        RoleEntity role =
                RoleEntity.builder()
                        .name("ROLE_ADMIN")
                        .build();

        RoleResponseDto response =
                RoleResponseDto.builder()
                        .name("ROLE_ADMIN")
                        .usersCount(0)
                        .build();

        when(roleRepository.findByNameIgnoreCase("ROLE_ADMIN"))
                .thenReturn(Optional.of(role));
        when(roleMapper.toResponseDto(role))
                .thenReturn(response);

        RoleResponseDto result =
                roleService.getRoleByName(" admin ");

        assertNotNull(result);
        assertEquals("ROLE_ADMIN", result.getName());

        verify(roleRepository)
                .findByNameIgnoreCase("ROLE_ADMIN");
        verify(roleMapper)
                .toResponseDto(role);
    }

    @Test
    void shouldReturnRoleByNameWhenPrefixAlreadyExists() {
        RoleEntity role =
                RoleEntity.builder()
                        .name("ROLE_MODERATOR")
                        .build();

        RoleResponseDto response =
                RoleResponseDto.builder()
                        .name("ROLE_MODERATOR")
                        .usersCount(0)
                        .build();

        when(roleRepository.findByNameIgnoreCase("ROLE_MODERATOR"))
                .thenReturn(Optional.of(role));
        when(roleMapper.toResponseDto(role))
                .thenReturn(response);

        RoleResponseDto result =
                roleService.getRoleByName("role_moderator");

        assertNotNull(result);
        assertEquals("ROLE_MODERATOR", result.getName());

        verify(roleRepository)
                .findByNameIgnoreCase("ROLE_MODERATOR");
    }

    @Test
    void shouldThrowExceptionWhenRoleByNameDoesNotExist() {
        when(roleRepository.findByNameIgnoreCase("ROLE_READER"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> roleService.getRoleByName("reader")
        );

        verify(roleRepository)
                .findByNameIgnoreCase("ROLE_READER");
        verify(roleMapper, never())
                .toResponseDto(any(RoleEntity.class));
    }
}
