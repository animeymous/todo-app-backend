package com.todo.todo_app_backend.dto;

import com.todo.todo_app_backend.entity.Role;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}