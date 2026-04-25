package com.todo.todo_app_backend.controller;

import com.todo.todo_app_backend.dto.*;
import com.todo.todo_app_backend.dto.RegisterRequestDto;
import com.todo.todo_app_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponseDto signup(@RequestBody RegisterRequestDto request) {
        System.out.println(request);
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto request) {
        System.out.println(request);
        return authService.login(request);
    }
}