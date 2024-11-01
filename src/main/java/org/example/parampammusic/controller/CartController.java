package org.example.parampammusic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.OrderItem;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.OrderService;
import org.example.parampammusic.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для управления действиями, связанными с корзиной покупок.
 */
@Controller
public class CartController {
    private static final Logger logger = LogManager.getLogger(CartController.class);


    private final OrderService orderService;
    private final UserService userService;

    /**
     * Конструктор для инициализации зависимостей.
     *
     * @param orderService сервис для работы с заказами
     */
    public CartController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Отображает корзину пользователя.
     *
     * @param model объект Model для передачи данных в представление
     * @return имя представления для страницы корзины
     */
    @GetMapping("/cart")
    @Transactional
    public String getOrder(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            logger.warn("Попытка доступа к корзине неавторизованного пользователя.");
            return "redirect:/login";
        }

        logger.info("Загрузка корзины для пользователя с ID {}", currentUser.getId());

        List<OrderItem> orderItems = orderService.getOrderItems();
        Hibernate.initialize(orderItems);
        if (orderItems == null) {
            logger.info("Корзина пользователя с ID {} пуста.", currentUser.getId());
            orderItems = new ArrayList<>();
        }
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalPrice", orderService.calculateTotalPrice());
        return "cart";
    }

    /**
     * Добавляет трек в корзину пользователя.
     *
     * @param audioTrackId ID добавляемого аудиотрека
     * @return перенаправление на страницу корзины
     */
    @PostMapping("/cart/addToCart")
    public String addTrackToCart(@RequestParam int audioTrackId, Model model) {
        User user = userService.getCurrentUser();
        logger.debug("Текущий пользователь: {}", user);
        if (user != null) {
            orderService.addTrackToOrder(audioTrackId, model);
            logger.info("Трек с ID {} успешно добавлен в корзину пользователя с ID {}.", audioTrackId, user.getId());
        } else {
            logger.warn("Пользователь не найден");
        }
        return "redirect:/cart";
    }


    /**
     * Удаляет трек из корзины пользователя.
     *
     * @param audioTrackId ID трека для удаления
     * @return перенаправление на страницу корзины
     */
    @PostMapping("/cart/removeFromCart")
    public String removeTrackFromOrder(@RequestParam int audioTrackId) {

        orderService.removeTrackFromOrder(audioTrackId);
        logger.info("Трек с ID {} успешно удален из корзины.", audioTrackId);
        return "redirect:/cart";
    }

    /**
     * Оформляет заказ для пользователя и завершает его.
     *
     * @return перенаправление на страницу с покупками пользователя
     */
    @PostMapping("/cart/checkout")
    public String checkout() {
        User user = userService.getCurrentUser();
        if (user != null) {
            orderService.completeOrder();
            logger.info("Заказ успешно оформлен для пользователя с ID {}.", user.getId());
        }
        return "redirect:/myTrack";
    }
}
