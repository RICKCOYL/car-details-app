package com.cardetails.controller;

import com.cardetails.entity.User;
import com.cardetails.model.Role;
import com.cardetails.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        HttpSession session) {
        Optional<User> user = userService.authenticate(username, password);
        
        if (user.isPresent()) {
            User authenticatedUser = user.get();
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("userId", authenticatedUser.getId());
            Role role = authenticatedUser.getRole();
            session.setAttribute("userRole", role);
            return "redirect:/car-form";
        }
        
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
