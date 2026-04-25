package com.todo.todo_app_backend.repository;

import com.todo.todo_app_backend.entity.Todo;
import com.todo.todo_app_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // Find all todos for a specific user (excluding soft deleted ones)
    List<Todo> findByUserAndIsDeletedFalse(User user);

    // Find a single todo by id and user (for security - ensure user owns the todo)
    java.util.Optional<Todo> findByIdAndUserAndIsDeletedFalse(Long id, User user);

    // Find todo by id and user (including deleted ones)
    java.util.Optional<Todo> findByIdAndUser(Long id, User user);
}