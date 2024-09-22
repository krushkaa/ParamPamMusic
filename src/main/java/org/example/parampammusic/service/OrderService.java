package org.example.parampammusic.service;

import org.example.parampammusic.entity.*;
import org.example.parampammusic.repository.OrderRepository;
import org.example.parampammusic.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AudioTrackService audioTrackService;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        AudioTrackService audioTrackService,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.audioTrackService = audioTrackService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order getOrder(User user) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = userDetails.getUsername();

        User currentUser = userRepository.findByLogin(login);

        if (currentUser == null) {
            throw new IllegalArgumentException("Current user not found");
        }

        OrderStatus status = OrderStatus.IN_CART;
        Integer orderId = currentUser.getId();
        if (orderId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return findOrderByIdAndStatus(orderId, status).orElseGet(() -> createOrder(currentUser));
    }

    private Optional<Order> findOrderByIdAndStatus(Integer orderId, OrderStatus status) {
        return orderRepository.findByIdAndStatus(orderId, status);
    }

    @Transactional
    public Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.IN_CART);
        order.setTotalPrice(0.0);
        order.setOrderItems(new ArrayList<>());
        return orderRepository.save(order);
    }

    @Transactional
    public void addTrackToOrder(User user, int audioTrackId) {
        Order order = getOrder(user);

        AudioTrack track = audioTrackService.getAudioTrackById(audioTrackId)
                .orElseThrow(() -> new RuntimeException("Трек не найден" + audioTrackId));

        boolean exists = order.getOrderItems().stream()
                .anyMatch(orderItem -> orderItem.getAudioTrack() != null &&
                        orderItem.getAudioTrack().getId() == (track.getId()));

        if (!exists) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setAudioTrack(track);
            orderItem.setPrice(track.getPrice());
            order.getOrderItems().add(orderItem);
            order.setTotalPrice(order.getTotalPrice() + track.getPrice());
            orderRepository.save(order);
        }
    }

    @Transactional
    public void removeTrackFromOrder(User user, int audioTrackId) {
        Order order = getOrder(user);
        List<OrderItem> items = order.getOrderItems();
        Optional<OrderItem> itemToRemove = items.stream()
                .filter(item -> item.getAudioTrack().getId() == audioTrackId)
                .findFirst();

        itemToRemove.ifPresent(item -> {
            order.setTotalPrice(order.getTotalPrice() - item.getPrice());
            items.remove(item);
            orderRepository.save(order);
        });
    }

    @Transactional
    public void completeOrder(User user) {
        Order order = getOrder(user);
        order.setStatus(OrderStatus.COMPLETED);
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);
    }

    public List<OrderItem> getCartItems(User user) {
        Order order = getOrder(user);
        return order.getOrderItems();
    }
}

