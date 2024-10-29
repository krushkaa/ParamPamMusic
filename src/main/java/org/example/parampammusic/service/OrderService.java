package org.example.parampammusic.service;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.ParamPamMusicApplication;
import org.example.parampammusic.entity.*;
import org.example.parampammusic.repository.OrderRepository;
import org.example.parampammusic.util.TrackNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сервис для управления заказами.
 * Предоставляет методы для создания, обновления и завершения заказов,
 * а также для добавления и удаления треков из корзины.
 */
@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

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
    @Transactional
    public Order getOrder() {
        User currentUser = userService.getCurrentUser();
        logger.info("Получение текущего заказа для пользователя с ID: {}", currentUser.getId());
        List<Order> orders = orderRepository.findByUserAndStatus(currentUser, OrderStatus.IN_CART);

        if (orders.isEmpty()) {
            logger.warn("Заказ не найден. Создание нового заказа.");
            return createOrder();
        } else {
            logger.info("Найден заказ с ID: {}", orders.get(0).getId());
            return orders.get(0);
        }
    }

    /**
     * Создает новый заказ для указанного пользователя.
     *
     * @return созданный заказ.
     */
    @Transactional
    public Order createOrder() {
        Order order = new Order();
        User currentUser = userService.getCurrentUser();
        order.setUser(currentUser);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.IN_CART);
        order.setTotalPrice(0.0);
        order.setOrderItems(new ArrayList<>());

        logger.info("Создан новый заказ с ID: {} для пользователя с ID: {}", order.getId(), currentUser.getId());
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
    public String addTrackToOrder(int audioTrackId, Model model) {
        User currentUser = userService.getCurrentUser();
        Order order = getOrder();
        logger.info("Текущий заказ: {}", order.getId());

        AudioTrack track = audioTrackService.getAudioTrackById(audioTrackId)
                .orElseThrow(() -> new TrackNotFoundException(audioTrackId));
        logger.info("Добавление трека ID: {}, Название: {}", track.getId(), track.getTitle());

        boolean trackInOrder = order.getOrderItems().stream()
                .anyMatch(item -> item.getAudioTrack().getId() == track.getId());

        if (trackInOrder) {
            model.addAttribute("message", "Трек уже в корзине");
            return "cart";
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setAudioTrack(track);
        orderItem.setPrice(track.getPrice());
        orderItem.setUser(currentUser);

        order.getOrderItems().add(orderItem);
        order.setTotalPrice(order.getTotalPrice() + track.getPrice());
        orderRepository.save(order);
        logger.info("Трек '{}' добавлен в заказ ID: {}. Новая общая стоимость: {}", track.getTitle(), order.getId(), order.getTotalPrice());

        model.addAttribute("message", "Трек успешно добавлен в корзину");
        return "cart";
    }

    /**
     * Удаляет трек из текущего заказа пользователя.
     *
     * @param audioTrackId идентификатор трека, который необходимо удалить.
     */
    @Transactional
    public void removeTrackFromOrder(int audioTrackId) {
        Order order = getOrder();
        logger.info("Удаление трека ID: {} из заказа ID: {}", audioTrackId, order.getId());

        boolean removed = order.getOrderItems().removeIf(item -> item.getAudioTrack().getId() == audioTrackId);
        if (removed) {
            recalculateTotalPrice(order);
            orderRepository.save(order);
            logger.info("Трек удален. Новая общая стоимость заказа ID {}: {}", order.getId(), order.getTotalPrice());
        } else {
            logger.warn("Трек ID: {} не найден в заказе ID: {}", audioTrackId, order.getId());
        }
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
        logger.info("Заказ ID: {} завершен.", order.getId());
    }

    /**
     * Возвращает список треков, добавленных в корзину текущего заказа пользователя.
     *
     * @return список элементов заказа (треков) в корзине.
     */
    @Transactional
    public List<OrderItem> getOrderItems() {
        Order currentOrder = getOrder();
        if (currentOrder != null) {
            logger.info("Получение списка треков для заказа ID: {}", currentOrder.getId());
            return orderRepository.findOrderItemsByOrderId(currentOrder.getId());
        } else {
            logger.warn("Активный заказ не найден.");
            return Collections.emptyList();
        }
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
        logger.debug("Пересчитана общая стоимость для заказа ID: {}. Новая стоимость: {}", order.getId(), totalPrice);

    }


    /**
     * Рассчитывает и возвращает общую стоимость текущего заказа пользователя.
     *
     * @return общая стоимость заказа.
     */
    public double calculateTotalPrice() {
        double totalPrice = getOrder().getTotalPrice();
        logger.debug("Общая стоимость текущего заказа: {}", totalPrice);
        return totalPrice;
    }

    /**
     * Сохраняет заказ в базе данных.
     *
     * @param order заказ для сохранения.
     */
    public void saveOrder(Order order) {
        orderRepository.save(order);
        logger.debug("Заказ ID: {} сохранен в базе данных.", order.getId());
    }
}
