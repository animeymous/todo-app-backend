package com.todo.todo_app_backend.service;

import com.todo.todo_app_backend.dto.*;
import com.todo.todo_app_backend.entity.Role;
import com.todo.todo_app_backend.entity.User;
import com.todo.todo_app_backend.dto.RegisterRequestDto;
import com.todo.todo_app_backend.repository.UserRepository;
import com.todo.todo_app_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponseDto signup(RegisterRequestDto request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDto(token);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDto(token);
    }
}