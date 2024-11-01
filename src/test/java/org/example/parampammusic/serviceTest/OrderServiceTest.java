package org.example.parampammusic.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.example.parampammusic.entity.*;
import org.example.parampammusic.repository.OrderRepository;
import org.example.parampammusic.service.AudioTrackService;
import org.example.parampammusic.service.OrderService;
import org.example.parampammusic.service.UserService;
import org.example.parampammusic.util.TrackNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

class OrderServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AudioTrackService audioTrackService;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private Model model;

    private User user;
    private Order order;
    private AudioTrack track;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);

        order = new Order();
        order.setId(1);
        order.setUser(user);
        order.setTotalPrice(0.0);

        track = new AudioTrack();
        track.setId(1);
        track.setTitle("Track Title");
        track.setPrice(10.0);
    }

    @Test
    void testAddTrackToOrder_Success() throws TrackNotFoundException {
        when(userService.getCurrentUser()).thenReturn(user);
        when(orderRepository.findByUserAndStatus(user, OrderStatus.IN_CART)).thenReturn(List.of(order));
        when(audioTrackService.getAudioTrackById(track.getId())).thenReturn(Optional.of(track));

        String viewName = orderService.addTrackToOrder(track.getId(), model);

        assertEquals("cart", viewName);
        assertEquals(1, order.getOrderItems().size());
        assertEquals(track.getPrice(), order.getTotalPrice());
        verify(orderRepository, times(1)).save(order);
        verify(model).addAttribute("message", "Трек успешно добавлен в корзину");
    }

    @Test
    void testAddTrackToOrder_TrackNotFound() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(orderRepository.findByUserAndStatus(user, OrderStatus.IN_CART)).thenReturn(List.of(order));
        when(audioTrackService.getAudioTrackById(track.getId())).thenReturn(Optional.empty());

        assertThrows(TrackNotFoundException.class, () -> orderService.addTrackToOrder(track.getId(), model));
    }

    @Test
    void testAddTrackToOrder_TrackAlreadyInOrder() throws TrackNotFoundException {
        OrderItem orderItem = new OrderItem();
        orderItem.setAudioTrack(track);
        order.getOrderItems().add(orderItem);

        when(userService.getCurrentUser()).thenReturn(user);
        when(orderRepository.findByUserAndStatus(user, OrderStatus.IN_CART)).thenReturn(List.of(order));
        when(audioTrackService.getAudioTrackById(track.getId())).thenReturn(Optional.of(track));

        String viewName = orderService.addTrackToOrder(track.getId(), model);

        assertEquals("cart", viewName);
        verify(model).addAttribute("message", "Трек уже в корзине");
        verify(orderRepository, never()).save(any(Order.class));
    }
}


