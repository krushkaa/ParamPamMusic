package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.*;
/**
 * Представляет роль пользователя в системе.
 * Роль содержит уникальный идентификатор и имя роли.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    /**
     * Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    /**
     * Название роли.
     * Обязательное и уникальное поле.
     */
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}