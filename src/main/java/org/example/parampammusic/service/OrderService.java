package org.example.parampammusic.service;

import lombok.SneakyThrows;
import org.example.parampammusic.entity.*;
import org.example.parampammusic.repository.OrderRepository;
import org.example.parampammusic.util.TrackNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления заказами.
 * Предоставляет методы для создания, обновления и завершения заказов,
 * а также для добавления и удаления треков из корзины.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AudioTrackService audioTrackService;
    private final UserService userService;

    /**
     * Конструктор для инициализации OrderService с заданными репозиториями.
     *
     * @param orderRepository   репозиторий для работы с заказами
     * @param audioTrackService сервис для работы с аудиотреками
     * @param userService       сервис для работы с пользователями
     */
    public OrderService(OrderRepository orderRepository,
                        AudioTrackService audioTrackService,
                        @Lazy UserService userService) {
        this.orderRepository = orderRepository;
        this.audioTrackService = audioTrackService;
        this.userService = userService;
    }

    /**
     * Возвращает текущий заказ пользователя со статусом "в корзине".
     * Если такой заказ не найден, создает новый.
     *
     * @return текущий заказ пользователя.
     */
    @Transactional(readOnly = true)
    public Order getOrder() {
        User currentUser = userService.getCurrentUser();
        return orderRepository.findByUserAndStatus(currentUser, OrderStatus.IN_CART)
                .orElseGet(() -> createOrder(currentUser));
    }

//    private Optional<Order> findOrderByUserAndStatus(User user, OrderStatus status) {
//        return orderRepository.findByUserAndStatus(user, status)
//                .stream()
//                .findFirst();
//    }

    /**
     * Создает новый заказ для указанного пользователя.
     *
     * @param user пользователь, для которого создается заказ.
     * @return созданный заказ.
     */
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

    /**
     * Добавляет трек в текущий заказ пользователя.
     * Если трек с указанным идентификатором не найден, выбрасывает исключение.
     *
     * @param audioTrackId идентификатор добавляемого трека.
     */
    @SneakyThrows
    @Transactional
    public void addTrackToOrder(int audioTrackId) {
        User currentUser = userService.getCurrentUser();
        Order order = getOrder();

        AudioTrack track = audioTrackService.getAudioTrackById(audioTrackId)
                .orElseThrow(() -> new TrackNotFoundException(audioTrackId));

//        boolean exists = order.getOrderItems().stream()
//                .anyMatch(orderItem -> orderItem.getAudioTrack().getId() == track.getId());
//
//        if (!exists) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setAudioTrack(track);
        orderItem.setPrice(track.getPrice());
        order.getOrderItems().add(orderItem);
        order.setTotalPrice(order.getTotalPrice() + track.getPrice());
        orderRepository.save(order);
        System.out.println("Трек добавлен в корзину: " + track.getTitle());
//        } else {
//            System.out.println("Трек уже в корзине: " + track.getTitle());
//        }

    }

    /**
     * Удаляет трек из текущего заказа пользователя.
     *
     * @param audioTrackId идентификатор трека, который необходимо удалить.
     */
    @Transactional
    public void removeTrackFromOrder(int audioTrackId) {
        Order order = getOrder();
        order.getOrderItems().removeIf(item -> item.getAudioTrack().getId() == audioTrackId);
        recalculateTotalPrice(order);
        orderRepository.save(order);
    }

    /**
     * Завершает текущий заказ пользователя, изменяя его статус на "завершен".
     */
    @Transactional
    public void completeOrder() {
        Order order = getOrder();
        order.setStatus(OrderStatus.COMPLETED);
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);
    }

    /**
     * Возвращает список треков, добавленных в корзину текущего заказа пользователя.
     *
     * @return список элементов заказа (треков) в корзине.
     */
    public List<OrderItem> getCartItems() {
        Order currentOrder = getOrder();
        return currentOrder.getOrderItems();
    }

    /**
     * Пересчитывает общую стоимость заказа.
     *
     * @param order заказ, для которого пересчитывается стоимость.
     */
    private void recalculateTotalPrice(Order order) {
        double totalPrice = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
        order.setTotalPrice(totalPrice);
    }


    /**
     * Рассчитывает и возвращает общую стоимость текущего заказа пользователя.
     *
     * @return общая стоимость заказа.
     */
    public double calculateTotalPrice() {
        return getOrder().getTotalPrice();
    }

    /**
     * Сохраняет заказ в базе данных.
     *
     * @param order заказ для сохранения.
     */
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
