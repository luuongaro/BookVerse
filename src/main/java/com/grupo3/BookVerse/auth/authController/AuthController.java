package com.grupo3.BookVerse.auth.authController;
import com.grupo3.BookVerse.auth.authService.AuthService;
import com.grupo3.BookVerse.auth.dtos.AuthRequest;
import com.grupo3.BookVerse.auth.dtos.AuthResponse;
import com.grupo3.BookVerse.auth.dtos.RegisterRequest;
import com.grupo3.BookVerse.features.users.dto.UserResponseDto;
import com.grupo3.BookVerse.features.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "Endpoints for user authentication and registration"
)
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user using email and password and returns a JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest authRequest
    ) {

        return ResponseEntity.ok(
                authService.authenticate(authRequest)
        );
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account in BookVerse."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email or username already exists", content = @Content)
    })
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody RegisterRequest registerRequest
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(registerRequest));
    }



}