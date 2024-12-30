package com.shadowfax.vacationbookingsystem.controller;

import com.shadowfax.vacationbookingsystem.model.User;
import com.shadowfax.vacationbookingsystem.service.UserFavoriteService;
import com.shadowfax.vacationbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Şifreleme için kullanıyoruz

    @Autowired
    private UserFavoriteService userFavoriteService;

    // Create a new user (register)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);  // Register user
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

    // Login a user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            String responseMessage = userService.verify(user);  // User authentication

            if ("Not success".equals(responseMessage)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            } else if ("Authentication failed".equals(responseMessage)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed.");
            } else if ("An error occurred".equals(responseMessage)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication.");
            }

            // Return JWT token on successful login
            return ResponseEntity.ok(responseMessage);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login.");
        }
    }

    // Get the current authenticated user
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        try {
            String username = authentication.getName();  // Get the username of the authenticated user
            User user = userService.findByUsername(username);  // Retrieve user from database
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get user details by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        try {
            User existingUser = userService.getUserById(userId);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            String username = userDetails.getUsername() != null ? userDetails.getUsername() : existingUser.getUsername();
            String email = userDetails.getEmail() != null ? userDetails.getEmail() : existingUser.getEmail();
            String password = userDetails.getPassword() != null ? userDetails.getPassword() : existingUser.getPassword();

            // Veritabanındaki stored procedure'u çağır
            userService.updateUser(userId, username, email, password);

            // Güncellenmiş kullanıcıyı geri döndür
            User updatedUser = userService.getUserById(userId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            // Hata mesajını logla
            System.err.println("Error occurred in updateUser: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            // Beklenmeyen diğer hatalar için loglama
            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            boolean isDeleted = userService.deleteUser(userId);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user.");
        }
    }

}
