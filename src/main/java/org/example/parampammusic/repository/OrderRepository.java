package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Order;
import org.example.parampammusic.entity.OrderStatus;
import org.example.parampammusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    Optional<Order> findByUserAndStatus(User user, OrderStatus status);

    List<Order> findByUserId(int userId);
}

