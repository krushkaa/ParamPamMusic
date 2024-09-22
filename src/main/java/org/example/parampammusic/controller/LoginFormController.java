package org.example.parampammusic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginFormController {

    @GetMapping("/custom-login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "loginError";
    }
}
