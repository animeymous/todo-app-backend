package com.todo.todo_app_backend.dto;

import lombok.Data;

@Data
public class UpdateProfileRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
}