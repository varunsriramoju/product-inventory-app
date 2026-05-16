package com.example.inventory.controller;

import com.example.inventory.model.LoginRequest;
import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    // ── POST /auth/login ──
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest request) {

        Optional<User> user = userRepo.findByUsername(request.getUsername());

        Map<String, String> response = new HashMap<>();

        if (user.isPresent() &&
                user.get().getPassword().equals(request.getPassword())) {
            // Login success
            response.put("status", "success");
            response.put("username", user.get().getUsername());
            return ResponseEntity.ok(response);
        } else {
            // Login failed
            response.put("status", "error");
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }

    // ── POST /auth/register ──
    // Use this once to create the admin user
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody User user) {
        userRepo.save(user);
        return ResponseEntity.ok("User created: " + user.getUsername());
    }
}
