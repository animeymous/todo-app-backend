package com.todo.todo_app_backend.controller;

import com.todo.todo_app_backend.dto.CreateTodoRequestDto;
import com.todo.todo_app_backend.dto.TodoResponseDto;
import com.todo.todo_app_backend.dto.UpdateTodoRequestDto;
import com.todo.todo_app_backend.entity.TodoStatus;
import com.todo.todo_app_backend.entity.User;
import com.todo.todo_app_backend.repository.TodoRepository;
import com.todo.todo_app_backend.repository.UserRepository;
import com.todo.todo_app_backend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponseDto createTodo(@RequestBody CreateTodoRequestDto request) {
        return todoService.createTodo(request);
    }

    @GetMapping
    public List<TodoResponseDto> getUserTodos() {
        return todoService.getUserTodos();
    }

    @GetMapping("/{id}")
    public TodoResponseDto getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PutMapping("/{id}")
    public TodoResponseDto updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequestDto request) {
        return todoService.updateTodo(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @PatchMapping("/{id}/restore")
    public TodoResponseDto restoreTodo(@PathVariable Long id) {
        return todoService.restoreTodo(id);
    }

    @PatchMapping("/{id}/status")
    public TodoResponseDto updateTodoStatus(
            @PathVariable Long id,
            @RequestParam TodoStatus status) {
        return todoService.updateTodoStatus(id, status);
    }

}