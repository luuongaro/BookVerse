package com.grupo3.BookVerse.features.users.service;

import com.grupo3.BookVerse.features.users.dto.UserRequestDto;
import com.grupo3.BookVerse.features.users.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDto createUser(UserRequestDto dto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByIdExternal(UUID idExternal);

    UserResponseDto updateUser(UUID idExternal, UserRequestDto dto);

    void deleteUser(UUID idExternal);
}