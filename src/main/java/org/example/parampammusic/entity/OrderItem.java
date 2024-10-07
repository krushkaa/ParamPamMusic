package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Представляет позицию в заказе.
 * Позиция включает информацию о заказе, аудиотреке и цене.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "order_item")
public class OrderItem {
    /**
     * Уникальный идентификатор позиции.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Заказ, к которому принадлежит позиция заказа.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Аудиотрек, который включен в заказ.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "audio_track_id", nullable = false)
    private AudioTrack audioTrack;

    /**
     * Цена позиции.
     * Обязательное поле.
     */
    @Column(name = "price", nullable = false)
    private double price;
}
