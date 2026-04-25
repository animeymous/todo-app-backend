package com.todo.todo_app_backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateTodoRequestDto {
    private String heading;
    private String description;
    private LocalDateTime dueDate;  // optional
}