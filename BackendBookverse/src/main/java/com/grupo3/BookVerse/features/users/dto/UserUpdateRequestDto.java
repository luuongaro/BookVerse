package com.grupo3.BookVerse.features.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDto(

        @NotBlank(message = "Username is required.")
        @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters.")
        String username,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email format is invalid.")
        String email
) {
}