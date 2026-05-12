package com.grupo3.BookVerse.features.users.domain.dto;

import com.grupo3.BookVerse.features.users.domain.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID idExternal,
        String username,
        String email
) {}

