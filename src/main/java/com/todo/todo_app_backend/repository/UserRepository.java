package com.todo.todo_app_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.todo.todo_app_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
