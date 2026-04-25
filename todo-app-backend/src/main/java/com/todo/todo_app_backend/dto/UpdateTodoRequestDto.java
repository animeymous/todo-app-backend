package com.todo.todo_app_backend.dto;

import com.todo.todo_app_backend.entity.TodoStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateTodoRequestDto {
    private String heading;
    private String description;
    private TodoStatus status;
    private LocalDateTime dueDate;
}