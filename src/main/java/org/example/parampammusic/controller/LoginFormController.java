package org.example.parampammusic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для управления формой логина и ошибками связанными со входом в приложение
 */
@Controller
public class LoginFormController {

    private final static Logger logger = LogManager.getLogger(LoginFormController.class);

    /**
     * Отображает форму для входа.
     *
     * @return представление формы входа
     */
    @GetMapping("/custom-login")
    public String loginForm() {
        logger.info("Открыта страница логина.");
        return "loginForm";
    }

    /**
     * Отображает страницу ошибки входа, если аутентификация не удалась.
     *
     * @param model объект Model для передачи данных на страницу
     * @return представление страницы ошибки входа
     */
    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        logger.error("Ошибка входа.");
        return "loginError";
    }
}
