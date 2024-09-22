package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Order;
import org.example.parampammusic.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByIdAndStatus(int id, OrderStatus status);
}

