package org.example.parampammusic.controller;

import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "profile";
    }

    @PostMapping("/profile/updateUser")
    public String updateUser(@RequestParam Integer userId, @RequestParam String email, @RequestParam String telNumber) {
        userService.updateUser(userId, email, telNumber);
        return "redirect:/profile";
    }

    @PostMapping("/profile/addPoint")
    public String addPoint(@RequestParam Integer userId, @RequestParam int bonusPoints) {
        userService.addBonusPoints(userId, bonusPoints);
        return "redirect:/profile";
    }

    @PostMapping("/profile/deleteUser")
    public String deleteUser(@RequestParam Integer userId) {
        userService.deleteUser(userId);
        return "redirect:/profile";
    }
}