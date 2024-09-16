package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "tel_number", nullable = false)
    private String telNumber;

    @Column(name = "bonus_point")
    private int bonusPoint;

    @ManyToOne
    @JoinColumn(name = "role_name", nullable = false)
    private Role role;

    public User(String testUser, String password) {
    }
}
