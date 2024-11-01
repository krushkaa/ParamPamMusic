package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Представляет сущность заказа.
 * Заказ содержит информацию о пользователе, общей цене, дате заказа,
 * позициях заказа и статусе.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {
    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Пользователь, сделавший заказ.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Общая цена заказа.
     * Обязательное поле.
     */
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    /**
     * Дата заказа.
     * Обязательное поле.
     */
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    /**
     * Позиции, входящие в заказ.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * Статус заказа.
     * Обязательное поле.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;
}



