package com.todo.todo_app_backend.dto;

import com.todo.todo_app_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
}
