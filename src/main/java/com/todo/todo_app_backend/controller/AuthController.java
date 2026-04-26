package com.todo.todo_app_backend.controller;

import com.todo.todo_app_backend.dto.ApiResponseDto;
import com.todo.todo_app_backend.dto.AuthResponseDto;
import com.todo.todo_app_backend.dto.LoginRequestDto;
import com.todo.todo_app_backend.dto.RegisterRequestDto;
import com.todo.todo_app_backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponseDto<AuthResponseDto> signup(@RequestBody RegisterRequestDto request, HttpServletRequest httpRequest) {
        AuthResponseDto response = authService.signup(request);
        return ApiResponseDto.success("User registered successfully", response, httpRequest.getRequestURI());
    }

    @PostMapping("/login")
    public ApiResponseDto<AuthResponseDto> login(@RequestBody LoginRequestDto request, HttpServletRequest httpRequest) {
        AuthResponseDto response = authService.login(request);
        return ApiResponseDto.success("Login successful", response, httpRequest.getRequestURI());
    }
}