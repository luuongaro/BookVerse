package com.grupo3.BookVerse.auth.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage = switch (authException) {
            case BadCredentialsException badCredentialsException ->
                    "Invalid credentials";
            case DisabledException disabledException ->
                    "Account disabled";
            case LockedException lockedException ->
                    "Account locked";
            case CredentialsExpiredException credentialsExpiredException ->
                    "Credentials expired";
            case InsufficientAuthenticationException insufficientAuthenticationException ->
                    "Insufficient authentication";
            case AuthenticationServiceException authenticationServiceException ->
                    "Authentication service error";
            default ->
                    "Unauthorized access";
        };

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", errorMessage);
        responseData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseData.put("path", request.getRequestURI());

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(responseData));
        response.getWriter().flush();
    }
}