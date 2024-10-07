package org.example.parampammusic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * Контроллер для управления формой логина и ошибками связанными со входом в приложение
 */
@Controller
public class LoginFormController {

    /**
     * Отображает форму для входа.
     *
     * @return представление формы входа
     */
    @GetMapping("/custom-login")
    public String loginForm() {
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
        return "loginError";
    }
}
