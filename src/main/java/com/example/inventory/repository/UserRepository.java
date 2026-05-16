package com.example.inventory.repository;

import com.example.inventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    // Find user by username
    Optional<User> findByUsername(String username);
}
