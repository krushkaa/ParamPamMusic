package org.example.parampammusic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * Контроллер для отображения приветственной страницы и главной страницы.
 */
@Controller
public class WelcomeController {
    /**
     * Обрабатывает GET-запрос для корневого URL и возвращает приветственную страницу.
     *
     * @return имя шаблона приветственной страницы
     */
    @GetMapping("/")
    public String home() {
        return "welcomePage";
    }

    /**
     * Обрабатывает GET-запрос для URL "/main" и возвращает главную страницу.
     *
     * @return имя шаблона главной страницы
     */
    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}


