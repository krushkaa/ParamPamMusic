package org.example.parampammusic.controllerTest;

import org.example.parampammusic.controller.CartController;
import org.example.parampammusic.service.OrderService;
import org.example.parampammusic.service.UserService;
import org.example.parampammusic.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.when;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

//    @Test
//    void testGetOrder() throws Exception {
//        User mockUser = new User();
//        when(userService.getCurrentUser()).thenReturn(mockUser);
//
//        mockMvc.perform(get("/cart"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("cart"));
//    }
}
