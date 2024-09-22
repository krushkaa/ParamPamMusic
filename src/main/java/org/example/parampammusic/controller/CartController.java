package org.example.parampammusic.controller;

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

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    private final OrderService orderService;
    private final UserService userService;

    public CartController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String getOrder(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        List<OrderItem> orderItems = orderService.getCartItems(user);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalPrice", orderService.getOrder(user).getTotalPrice());
        return "cart";
    }

    @PostMapping("/cart/addToCart")
    public String addTrackToCart (@RequestParam int audioTrackId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByLogin(principal.getName());
        if (user != null) {
            orderService.addTrackToOrder(user, audioTrackId);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/removeFromCart")
    public String removeTrackFromOrder(@RequestParam int audioTrackId, @AuthenticationPrincipal User user) {
        orderService.removeTrackFromOrder(user, audioTrackId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(@AuthenticationPrincipal User user) {
        orderService.completeOrder(user);
        return "redirect:/my_track";
    }
}

