package com.todo.todo_app_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String path;

    // Pagination fields (only for list endpoints)
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageSize;

    // Helper method to create success response
    public static <T> ApiResponseDto<T> success(String message, T data, String path) {
        return ApiResponseDto.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    // Helper method for success without data (DELETE, etc.)
    public static ApiResponseDto<Void> success(String message, String path) {
        return ApiResponseDto.<Void>builder()
                .status(200)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    // Helper method for paginated response
    public static <T> ApiResponseDto<T> paginated(String message, T data, String path,
                                               int currentPage, int totalPages,
                                               long totalElements, int pageSize) {
        return ApiResponseDto.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(path)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .pageSize(pageSize)
                .build();
    }

    // Helper method for error response
    public static ApiResponseDto<Void> error(int status, String message, String path) {
        return ApiResponseDto.<Void>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
}