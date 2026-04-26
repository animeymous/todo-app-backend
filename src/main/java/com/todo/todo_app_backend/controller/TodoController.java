package com.todo.todo_app_backend.controller;

import com.todo.todo_app_backend.dto.ApiResponseDto;
import com.todo.todo_app_backend.dto.CreateTodoRequestDto;
import com.todo.todo_app_backend.dto.TodoResponseDto;
import com.todo.todo_app_backend.dto.UpdateTodoRequestDto;
import com.todo.todo_app_backend.entity.TodoStatus;
import com.todo.todo_app_backend.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<TodoResponseDto> createTodo(@RequestBody CreateTodoRequestDto request, HttpServletRequest httpRequest) {
        TodoResponseDto todo = todoService.createTodo(request);
        return ApiResponseDto.success("Todo created successfully", todo, httpRequest.getRequestURI());
    }

    @GetMapping
    public ApiResponseDto<List<TodoResponseDto>> getUserTodos(HttpServletRequest httpRequest) {
        List<TodoResponseDto> todos = todoService.getUserTodos();
        return ApiResponseDto.success("Todos retrieved successfully", todos, httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    public ApiResponseDto<TodoResponseDto> getTodoById(@PathVariable Long id, HttpServletRequest httpRequest) {
        TodoResponseDto todo = todoService.getTodoById(id);
        return ApiResponseDto.success("Todo retrieved successfully", todo, httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    public ApiResponseDto<TodoResponseDto> updateTodo(@PathVariable Long id,
                                                   @RequestBody UpdateTodoRequestDto request,
                                                   HttpServletRequest httpRequest) {
        TodoResponseDto updatedTodo = todoService.updateTodo(id, request);
        return ApiResponseDto.success("Todo updated successfully", updatedTodo, httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteTodo(@PathVariable Long id, HttpServletRequest httpRequest) {
        todoService.deleteTodo(id);
        return ApiResponseDto.success("Todo deleted successfully", httpRequest.getRequestURI());
    }

    @PatchMapping("/{id}/restore")
    public ApiResponseDto<TodoResponseDto> restoreTodo(@PathVariable Long id, HttpServletRequest httpRequest) {
        TodoResponseDto restoredTodo = todoService.restoreTodo(id);
        return ApiResponseDto.success("Todo restored successfully", restoredTodo, httpRequest.getRequestURI());
    }

    @PatchMapping("/{id}/status")
    public ApiResponseDto<TodoResponseDto> updateTodoStatus(@PathVariable Long id,
                                                         @RequestParam TodoStatus status,
                                                         HttpServletRequest httpRequest) {
        TodoResponseDto updatedTodo = todoService.updateTodoStatus(id, status);
        return ApiResponseDto.success("Todo status updated successfully", updatedTodo, httpRequest.getRequestURI());
    }
}