package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Order;
import org.example.parampammusic.entity.OrderItem;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.OrderService;
import org.example.parampammusic.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Контроллер для управления действиями, связанными с корзиной покупок.
 */
@Controller
public class CartController {

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
     * @param user  текущий авторизованный пользователь
     * @return имя представления для страницы корзины
     */
    @GetMapping("/cart")
    public String getOrder(Model model, @AuthenticationPrincipal User user) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        Order cart = orderService.getOrder();
        List<OrderItem> orderItems = orderService.getCartItems();
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
    public String addTrackToCart(@RequestParam int audioTrackId) {
        User user = userService.getCurrentUser();
        if (user != null) {
            orderService.addTrackToOrder(audioTrackId);
        }
        return "redirect:/cart";
    }


    /**
     * Удаляет трек из корзины пользователя.
     *
     * @param audioTrackId ID трека для удаления
     * @param user         текущий авторизованный пользователь
     * @return перенаправление на страницу корзины
     */
    @PostMapping("/cart/removeFromCart")
    public String removeTrackFromOrder(@RequestParam int audioTrackId, @AuthenticationPrincipal User user) {
        orderService.removeTrackFromOrder(audioTrackId);
        return "redirect:/cart";
    }

    /**
     * Оформляет заказ для пользователя и завершает его.
     *
     * @param user текущий авторизованный пользователь
     * @return перенаправление на страницу с покупками пользователя
     */
    @PostMapping("/cart/checkout")
    public String checkout(@AuthenticationPrincipal User user) {
        orderService.completeOrder();
        return "redirect:/my_track";
    }
}

