package org.example.parampammusic.entity;
/**
 * Перечисление для статусов заказа.
 * Включает возможные статусы, которые может иметь заказ.
 */
public enum OrderStatus {
    /**
     * Заказ находится в корзине.
     */
    IN_CART,

    /**
     * Заказ завершен.
     */
    COMPLETED,

    /**
     * Заказ отменен.
     */
    CANCELLED
}

