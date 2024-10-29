package org.example.parampammusic.controller;

import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * Контроллер для отображения приветственной страницы и главной страницы.
 */
@Controller
public class WelcomeController {
    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
    private final UserService userService;

    public WelcomeController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обрабатывает GET-запрос для корневого URL и возвращает приветственную страницу.
     *
     * @return имя шаблона приветственной страницы
     */
    @GetMapping("/")
    public String home() {
        logger.info("Открыта титульная страница.");
        return "welcomePage";
    }

    /**
     * Обрабатывает GET-запрос для URL "/main" и возвращает главную страницу.
     *
     * @return имя шаблона главной страницы
     */
    @GetMapping("/main")
    public String mainPage() {
        User currentUser = userService.getCurrentUser();
        logger.info("Открыта главная страница. Активный пользователь: {}", currentUser.getLogin());
        return "main";
    }
}


