package org.example.parampammusic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/welcomePage")
    public String home() {
        return "welcomePage";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "/loginForm";
    }

    @GetMapping("/registration")
    public String registration() {
        return "/registration";
    }
}


