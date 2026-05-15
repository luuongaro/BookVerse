package com.grupo3.BookVerse.features.users.domain.dto;

import java.util.UUID;

public record UserResponseDto(
        UUID idExternal,
        String username,
        String email,
        String status
) {}
