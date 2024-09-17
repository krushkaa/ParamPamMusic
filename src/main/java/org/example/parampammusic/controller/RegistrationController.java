package org.example.parampammusic.controller;

import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.RoleService;
import org.example.parampammusic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RoleService roleService;
    private final UserService userService;

    public RegistrationController(UserService getUserServiceImpl, RoleService roleService) {
        this.userService = getUserServiceImpl;
        this.roleService = roleService;
    }

    @GetMapping("/registrationForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        String rawPassword = user.getPassword();
        int roleId = 2;
        userService.createUser(user, rawPassword, roleId);
        model.addAttribute("successMessage", "Registration successful!");
        return "registrationSuccess";
    }
}

