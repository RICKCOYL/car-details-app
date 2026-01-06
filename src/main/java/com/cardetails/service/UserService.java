package com.cardetails.service;

import com.cardetails.entity.User;
import com.cardetails.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Authenticates a user with username and password.
     * Business logic: Validates credentials and returns the authenticated user.
     */
    public Optional<User> authenticate(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return Optional.empty();
        }
        
        Optional<User> user = userRepository.findByUsername(username.trim());
        if (user.isPresent() && isPasswordValid(user.get(), password)) {
            return user;
        }
        return Optional.empty();
    }

    /**
     * Validates password for a user.
     * Business logic: Encapsulates password validation rules.
     * Currently uses simple comparison, but can be enhanced with hashing.
     */
    private boolean isPasswordValid(User user, String password) {
        Objects.requireNonNull(user, "User cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        // In production, use password hashing like BCrypt
        return user.getPassword().equals(password);
    }

    /**
     * Finds a user by username.
     * Business logic: Encapsulates user lookup.
     */
    public Optional<User> findByUsername(String username) {
        Objects.requireNonNull(username, "Username cannot be null");
        return userRepository.findByUsername(username.trim());
    }

    /**
     * Finds a user by ID.
     * Business logic: Encapsulates user lookup by identifier.
     */
    public Optional<User> findById(Integer id) {
        Objects.requireNonNull(id, "User ID cannot be null");
        return userRepository.findById(id);
    }

    /**
     * Checks if a user has admin role.
     * Business logic: Encapsulates role checking.
     */
    public boolean isAdmin(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        return "ADMIN".equals(user.getRole());
    }

}
