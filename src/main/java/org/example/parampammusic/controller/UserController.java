package org.example.parampammusic.controller;

import org.example.parampammusic.entity.OrderItem;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.OrderItemService;
import org.example.parampammusic.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
/**
 * Контроллер для управления действиями пользователей.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final OrderItemService orderItemService;
    /**
     * Конструктор контроллера пользователей.
     *
     * @param userService сервис для управления пользователями
     * @param orderItemService сервис для управления заказанными треками
     */
    public UserController(UserService userService, OrderItemService orderItemService) {
        this.userService = userService;
        this.orderItemService = orderItemService;
    }

    /**
     * Возвращает список приобретённых аудиотреков для указанного пользователя.
     *
     * @return ResponseEntity, содержащее список аудиотреков или код состояния 204, если треки не найдены
     */
    @GetMapping("/myTrack")
    public String myTracks(Model model) {
        User currentUser = userService.getCurrentUser();
        List<OrderItem> purchasedTracks = orderItemService.getPurchasedTracksByUser(currentUser);
        logger.info("Купленные треки для пользователя {}: {}", currentUser.getLogin(), purchasedTracks);

        model.addAttribute("user", currentUser);
        model.addAttribute("purchasedTracks", purchasedTracks);
        return "myTrack";
    }

    @GetMapping("/user/someEndpoint")
    public ResponseEntity<String> someEndpoint() {
        return ResponseEntity.ok("User access granted.");
    }
}
