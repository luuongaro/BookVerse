package com.grupo3.BookVerse.features.roles.controller;

import com.grupo3.BookVerse.features.roles.dto.RoleRequestDto;
import com.grupo3.BookVerse.features.roles.dto.RoleResponseDto;
import com.grupo3.BookVerse.features.roles.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto) {
        RoleResponseDto createdRole = roleService.createRole(roleRequestDto);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<RoleResponseDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{idExternal}")
    public ResponseEntity<RoleResponseDto> getRoleByIdExternal(@PathVariable UUID idExternal) {
        RoleResponseDto role = roleService.getRoleByIdExternal(idExternal);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/{idExternal}")
    public ResponseEntity<RoleResponseDto> updateRole(
            @PathVariable UUID idExternal,
            @Valid @RequestBody RoleRequestDto roleRequestDto) {

        RoleResponseDto updatedRole = roleService.updateRole(idExternal, roleRequestDto);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{idExternal}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID idExternal) {
        roleService.deleteRole(idExternal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleResponseDto> getRoleByName(@PathVariable String name) {
        RoleResponseDto role = roleService.getRoleByName(name);
        return ResponseEntity.ok(role);
    }
}