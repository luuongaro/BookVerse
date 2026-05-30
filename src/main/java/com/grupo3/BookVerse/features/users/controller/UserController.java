package com.grupo3.BookVerse.features.users.controller;

import com.grupo3.BookVerse.features.users.dto.UserRequestDto;
import com.grupo3.BookVerse.features.users.dto.UserResponseDto;
import com.grupo3.BookVerse.features.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto dto) {
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{idExternal}")
    public UserResponseDto getUserByIdExternal(@PathVariable UUID idExternal) {
        return userService.getUserByIdExternal(idExternal);
    }

    @PutMapping("/{idExternal}")
    public UserResponseDto updateUser(@PathVariable UUID idExternal,
                                      @Valid @RequestBody UserRequestDto dto) {
        return userService.updateUser(idExternal, dto);
    }

    @DeleteMapping("/{idExternal}")
    public void deleteUser(@PathVariable UUID idExternal) {
        userService.deleteUser(idExternal);
    }
}