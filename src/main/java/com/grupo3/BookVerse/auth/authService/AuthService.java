package com.grupo3.BookVerse.auth.authService;


import com.grupo3.BookVerse.auth.dtos.AuthRequest;
import com.grupo3.BookVerse.auth.dtos.AuthResponse;

public interface AuthService {

    AuthResponse authenticate(AuthRequest request);

}