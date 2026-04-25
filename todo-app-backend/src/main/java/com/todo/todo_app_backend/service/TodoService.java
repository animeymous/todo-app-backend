package com.todo.todo_app_backend.service;

import com.todo.todo_app_backend.dto.CreateTodoRequestDto;
import com.todo.todo_app_backend.dto.TodoResponseDto;
import com.todo.todo_app_backend.dto.UpdateTodoRequestDto;
import com.todo.todo_app_backend.entity.Todo;
import com.todo.todo_app_backend.entity.TodoStatus;
import com.todo.todo_app_backend.entity.User;
import com.todo.todo_app_backend.repository.TodoRepository;
import com.todo.todo_app_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // Create a new todo
    public TodoResponseDto createTodo(CreateTodoRequestDto request) {
        // Get currently logged in user
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create new todo
        Todo todo = Todo.builder()
                .heading(request.getHeading())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(TodoStatus.PENDING)
                .user(user)
                .build();

        Todo savedTodo = todoRepository.save(todo);
        return mapToDto(savedTodo);
    }

    // Get all todos for current user
    public List<TodoResponseDto> getUserTodos() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return todoRepository.findByUserAndIsDeletedFalse(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Helper method to convert entity to DTO
    private TodoResponseDto mapToDto(Todo todo) {
        return TodoResponseDto.builder()
                .id(todo.getId())
                .heading(todo.getHeading())
                .description(todo.getDescription())
                .status(todo.getStatus())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .dueDate(todo.getDueDate())
                .build();
    }

    // Get single todo by ID (only if belongs to current user)
    public TodoResponseDto getTodoById(Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = todoRepository.findByIdAndUserAndIsDeletedFalse(id, user)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        return mapToDto(todo);
    }

    // Update existing todo
    public TodoResponseDto updateTodo(Long id, UpdateTodoRequestDto request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = todoRepository.findByIdAndUserAndIsDeletedFalse(id, user)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        // Update only fields that are provided (not null)
        if (request.getHeading() != null) {
            todo.setHeading(request.getHeading());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            todo.setStatus(request.getStatus());
        }
        if (request.getDueDate() != null) {
            todo.setDueDate(request.getDueDate());
        }

        Todo updatedTodo = todoRepository.save(todo);
        return mapToDto(updatedTodo);
    }

    // Soft delete todo
    @Transactional
    public void deleteTodo(Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = todoRepository.findByIdAndUserAndIsDeletedFalse(id, user)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setDeleted(true);
        todo.setDeletedAt(LocalDateTime.now());
        todoRepository.save(todo);
    }

    // Restore soft deleted todo
    @Transactional
    public TodoResponseDto restoreTodo(Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find deleted todo that belongs to this user
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        if (!todo.isDeleted()) {
            throw new RuntimeException("Todo is not deleted");
        }

        todo.setDeleted(false);
        todo.setDeletedAt(null);
        Todo restoredTodo = todoRepository.save(todo);
        return mapToDto(restoredTodo);
    }

    // Update only the status (useful for toggling complete/pending)
    @Transactional
    public TodoResponseDto updateTodoStatus(Long id, TodoStatus status) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = todoRepository.findByIdAndUserAndIsDeletedFalse(id, user)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setStatus(status);
        Todo updatedTodo = todoRepository.save(todo);
        return mapToDto(updatedTodo);
    }
}