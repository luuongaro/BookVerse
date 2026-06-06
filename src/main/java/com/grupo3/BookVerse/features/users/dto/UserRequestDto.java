package com.grupo3.BookVerse.features.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;


public record UserRequestDto(

        @NotBlank(message = "Username is required.")
        @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters.")
        String username,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email format is invalid.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$",
                message = "Email must be valid and end with .com (e.g., name@domain.com)."
        )
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, max = 10, message = "Password must be between 8 and 10 characters.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,10}$",
                message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character (@$!%*?&._-)."
        )
        String password,

        @NotNull(message = "Subscription id is required.")
        UUID subscriptionId
) {}
