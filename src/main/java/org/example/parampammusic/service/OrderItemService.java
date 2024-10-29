package org.example.parampammusic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.OrderItem;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private static final Logger logger = LogManager.getLogger(OrderItemService.class);

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getPurchasedTracksByUser(User user) {
        return orderItemRepository.findByUser(user);
    }
}