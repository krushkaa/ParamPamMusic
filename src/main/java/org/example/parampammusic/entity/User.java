package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
/**
 * Представляет сущность пользователя.
 * Пользователь имеет уникальный идентификатор, логин, пароль, электронную почту, номер телефона,
 * бонусные очки, роль и список заказов.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "\"user\"")
public class User {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Логин пользователя.
     * Обязательное и уникальное поле.
     */
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    /**
     * Пароль пользователя.
     * Обязательное поле.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Электронная почта пользователя.
     * Обязательное и уникальное поле.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Номер телефона пользователя.
     * Обязательное поле.
     */
    @Column(name = "tel_number", nullable = false)
    private String telNumber;

    /**
     * Бонусные очки пользователя.
     */
    @Column(name = "bonus_point")
    private int bonusPoint;

    /**
     * Роль пользователя.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     * Заказы, сделанные пользователем.
     */
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
