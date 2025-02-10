package org.tasks.reservation.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tasks.reservation.entity.Users;
import org.tasks.reservation.helper.Roles;
import org.tasks.reservation.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class AuthViewController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been logged out.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(defaultValue = "CUSTOMER") Roles role,
                           Model model) {
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("errorMsg", "User already exists");
            return "register";
        }
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setUserRole(role);
        userRepository.save(newUser);
        model.addAttribute("msg", "User registered successfully. Please login.");
        return "login";
    }
}
