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
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && bonusPoint == user.bonusPoint && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(telNumber, user.telNumber) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, telNumber, bonusPoint, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", bonusPoints=" + bonusPoint +
                ", role=" + role +
                '}';
    }
}
