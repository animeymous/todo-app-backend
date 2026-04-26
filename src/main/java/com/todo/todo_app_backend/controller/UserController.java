package com.todo.todo_app_backend.controller;

import com.todo.todo_app_backend.dto.ApiResponseDto;
import com.todo.todo_app_backend.dto.ChangePasswordRequestDto;
import com.todo.todo_app_backend.dto.UpdateProfileRequestDto;
import com.todo.todo_app_backend.dto.UserProfileDto;
import com.todo.todo_app_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get current user profile
    @GetMapping("/profile")
    public ApiResponseDto<UserProfileDto> getUserProfile(HttpServletRequest request) {
        UserProfileDto profile = userService.getCurrentUserProfile();
        return ApiResponseDto.success("Profile retrieved successfully", profile, request.getRequestURI());
    }

    // Update user profile
    @PutMapping("/profile")
    public ApiResponseDto<UserProfileDto> updateUserProfile(
            @Valid @RequestBody UpdateProfileRequestDto requestDto,
            HttpServletRequest request) {
        UserProfileDto updatedProfile = userService.updateUserProfile(requestDto);
        return ApiResponseDto.success("Profile updated successfully", updatedProfile, request.getRequestURI());
    }

    // Change password
    @PostMapping("/change-password")
    public ApiResponseDto<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequestDto requestDto,
            HttpServletRequest request) {
        userService.changePassword(requestDto);
        return ApiResponseDto.success("Password changed successfully", request.getRequestURI());
    }
}