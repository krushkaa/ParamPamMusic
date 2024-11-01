package org.example.parampammusic.repository;

import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.OrderItem;
import org.example.parampammusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByUser(User user);
}
