package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
/**
 * Представляет отзыв пользователя о аудиотреке.
 * Отзыв включает информацию о пользователе, аудиотреке, рейтинге, комментарии и дате отзыва.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "review")
public class Review {
    /**
     * Уникальный идентификатор отзыва.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int review_id;

    /**
     * Пользователь, оставивший отзыв.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Аудиотрек, к которому относится отзыв.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "audio_track_id", nullable = false)
    private AudioTrack audioTrack;

    /**
     * Рейтинг отзыва (от 1 до 5).
     * Обязательное поле.
     */
    @Column(name = "rating", nullable = false)
    private int rating;

    /**
     * Текст комментария отзыва.
     * Обязательное поле.
     */
    @Column(name = "comment", nullable = false)
    private String comment;

    /**
     * Дата, когда был оставлен отзыв.
     * Обязательное поле.
     */
    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;
}
