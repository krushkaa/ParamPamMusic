package org.example.parampammusic.controller;

import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.RoleService;
import org.example.parampammusic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * Контроллер для обработки регистрации пользователей.
 */
@Controller
public class RegistrationController {

    private final RoleService roleService;
    private final UserService userService;
    /**
     * Конструктор контроллера регистрации.
     *
     * @param userService   сервис для управления пользователями
     * @param roleService   сервис для управления ролями
     */
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Отображает форму регистрации.
     *
     * @param model объект Model для передачи данных на страницу
     * @return представление страницы регистрации
     */
    @GetMapping("/registrationForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "registration";
    }

    /**
     * Обрабатывает регистрацию нового пользователя.
     *
     * @param user  объект пользователя, введённого в форму регистрации
     * @param model объект Model для передачи сообщений на страницу
     * @return представление страницы успешной регистрации
     */
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        String rawPassword = user.getPassword();
        int roleId = 2;
        userService.createUser(user, rawPassword, roleId);
        model.addAttribute("successMessage", "Registration successful!");
        return "registrationSuccess";
    }
}

