package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Представляет сущность альбома, который содержит информацию о музыкальных альбомах.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "album")
public class Album {
    /**
     * Уникальный идентификатор альбома.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Заголовок альбома.
     * Обязательное поле.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Дата выпуска альбома.
     */
    @Column(name = "release_date")
    private LocalDate releaseDate;

    /**
     * Список аудиотреков, принадлежащих этому альбому.
     * Поле поддерживает каскадное удаление.
     */
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AudioTrack> audioTrack = new ArrayList<>();
}
