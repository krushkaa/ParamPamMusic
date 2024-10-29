package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Order;
import org.example.parampammusic.entity.OrderItem;
import org.example.parampammusic.entity.OrderStatus;
import org.example.parampammusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Order.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как поиск заказа по идентификатору и статусу.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * Находит заказ по идентификатору и статусу.
     *
     * @param user   идентифицированный пользователь
     * @param status статус заказа
     * @return Optional, содержащий найденный заказ, если он существует
     */
    List<Order> findByUserAndStatus(User user, OrderStatus status);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findOrderItemsByOrderId(@Param("orderId") int orderId);
}

