package org.example.parampammusic.controller;

import org.example.parampammusic.DTO.UserDTO;
import org.example.parampammusic.entity.Role;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.RoleService;
import org.example.parampammusic.util.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/registrationForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        Role role = roleService.getRoleByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userService.createUser(new UserDTO());

        model.addAttribute("successMessage", "Registration successful!");
        return "loginForm";  // После регистрации перенаправляем на страницу логина
    }
}

