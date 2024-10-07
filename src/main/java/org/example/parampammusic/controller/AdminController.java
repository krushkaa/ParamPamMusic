package org.example.parampammusic.controller;

import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Контроллер для управления действиями администратора, связанными с пользователями.
 */

@Controller
public class AdminController {

    private final UserService userService;
    /**
     * Конструктор, инициализирующий AdminController с UserService.
     *
     * @param userService сервис для работы с пользователями
     */
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает страницу профиля с информацией о текущем пользователе и списком всех пользователей.
     *
     * @param model объект Model для передачи данных в представление
     * @return имя представления для страницы профиля
     */
    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "profile";
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param userId ID пользователя
     * @param email новый email пользователя
     * @param telNumber новый номер телефона пользователя
     * @return перенаправление на страницу профиля
     */
    @PostMapping("/profile/updateUser")
    public String updateUser(@RequestParam Integer userId, @RequestParam String email, @RequestParam String telNumber) {
        userService.updateUser(userId, email, telNumber);
        return "redirect:/profile";
    }

    /**
     * Добавляет бонусные баллы пользователю.
     *
     * @param userId ID пользователя
     * @param bonusPoints количество бонусных баллов для добавления
     * @return перенаправление на страницу профиля
     */
    @PostMapping("/profile/addPoint")
    public String addPoint(@RequestParam Integer userId, @RequestParam int bonusPoints) {
        userService.addBonusPoints(userId, bonusPoints);
        return "redirect:/profile";
    }

    /**
     * Удаляет пользователя.
     *
     * @param userId ID пользователя для удаления
     * @return перенаправление на страницу профиля
     */
    @PostMapping("/profile/deleteUser")
    public String deleteUser(@RequestParam Integer userId) {
        userService.deleteUser(userId);
        return "redirect:/profile";
    }
}