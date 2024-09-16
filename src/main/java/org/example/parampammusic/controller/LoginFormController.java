package org.example.parampammusic.controller;

import jakarta.servlet.http.HttpSession;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginFormController {

    private final UserService userService;

    public LoginFormController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/custom-login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "loginError";
    }
    @PostMapping("/loginForm")
    public String login(@RequestParam String login, @RequestParam String rawPassword, HttpSession session, Model model) {
        if (userService.authenticate(login, rawPassword, session)) {
            User user = (User) userService.loadUserByUsername(login);
            session.setAttribute("username", user.getLogin());
            return "redirect:main";
        } else {
            model.addAttribute("error", "Неправильный логин или пароль");
            return "loginError";
        }
    }
}
