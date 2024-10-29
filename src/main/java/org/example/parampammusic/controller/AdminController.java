package org.example.parampammusic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.UserService;
import org.springframework.http.ResponseEntity;
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
    private static final Logger logger = LogManager.getLogger(AdminController.class);
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
        logger.info("Профильная страница отображена для пользователя с ID: {}", user.getId());
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
        try {
            userService.updateUser(userId, email, telNumber);
            logger.info("Информация о пользователе с ID {} успешно обновлена. Новый email: {}, новый номер телефона: {}", userId, email, telNumber);
        } catch (Exception e) {
            logger.error("Ошибка при обновлении пользователя с ID {}: {}", userId, e.getMessage());
        }
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
        try {
            userService.addBonusPoints(userId, bonusPoints);
            logger.info("Добавлено {} бонусных баллов пользователю с ID {}", bonusPoints, userId);
        } catch (Exception e) {
            logger.error("Ошибка при добавлении бонусных баллов пользователю с ID {}: {}", userId, e.getMessage());
        }
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
        try {
            userService.deleteUser(userId);
            logger.info("Пользователь с ID {} был успешно удален", userId);
        } catch (Exception e) {
            logger.error("Ошибка при удалении пользователя с ID {}: {}", userId, e.getMessage());
        }
        return "redirect:/profile";
    }
    @GetMapping("/admin/someEndpoint")
    public ResponseEntity<String> someEndpoint() {
        return ResponseEntity.ok("Admin access granted.");
    }
}
