package com.todo.todo_app_backend.dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}