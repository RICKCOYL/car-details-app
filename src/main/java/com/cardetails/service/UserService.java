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

    private boolean isPasswordValid(User user, String password) {
        Objects.requireNonNull(user, "User cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        return user.getPassword().equals(password);
    }


}
