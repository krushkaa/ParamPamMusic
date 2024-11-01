package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Представляет сущность аудиотрека.
 * Содержит информацию о заголовке, артисте, жанре, альбоме и цене.
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "audio_track")
public class AudioTrack {
    /**
     * Уникальный идентификатор аудиотрека.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Заголовок аудиотрека.
     * Обязательное поле.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Исполнитель, которому принадлежит аудиотрек.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    /**
     * Жанр аудиотрека.
     */
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    /**
     * Альбом, к которому принадлежит аудиотрек.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    /**
     * Цена аудиотрека.
     * Обязательное поле.
     */
    @Column(name = "price", nullable = false)
    private double price;
}
