package org.example.parampammusic.controller;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.parampammusic.entity.OrderItem;
import org.example.parampammusic.entity.User;

import java.time.LocalDate;
import java.util.List;

public class Model {
    @Data
    @Accessors(chain = true)
    public static class UserResponse {
        private Integer id;
        private String login;
        private String password;
        private String email;
        private String telNumber;
        private Integer bonusPoint;
    }

    @Data
    @Accessors(chain = true)
    public static class CreateUserRequest {
        private Integer id;
        private String login;
        private String password;
        private String email;
        private String telNumber;
        private String bonusPoint;
    }

    @Data
    @Accessors(chain = true)
    public static class OrderResponse {
        private Integer id;
        private User user;
        private double totalPrice;
        private LocalDate orderDate;
        private List<OrderItem> orderItems;
    }

    @Data
    @Accessors(chain = true)
    public static class CreateOrderRequest {
        private Integer id;
        private User user;
        private double totalPrice;
        private LocalDate orderDate;
        private List<OrderItem> orderItems;
    }
}
