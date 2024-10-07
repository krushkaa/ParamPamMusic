package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.*;
/**
 * Представляет сущность жанра музыкального трека.
 * Жанр имеет уникальный идентификатор и имя.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "genre")
public class Genre {
    /**
     * Уникальный идентификатор жанра.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Название жанра.
     */
    @Column (name = "genre_name", nullable = false)
    private String genreName;
}
