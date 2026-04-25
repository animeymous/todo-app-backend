package com.todo.todo_app_backend.dto;

import com.todo.todo_app_backend.entity.TodoStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TodoResponseDto {
    private Long id;
    private String heading;
    private String description;
    private TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
}