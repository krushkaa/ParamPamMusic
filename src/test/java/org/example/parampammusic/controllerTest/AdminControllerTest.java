package org.example.parampammusic.controllerTest;

import org.example.parampammusic.controller.AdminController;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private Model model;

    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setTelNumber("123456789");

        userList = new ArrayList<>();
        userList.add(user);
    }

//    @Test
//    void testShowProfilePage() throws Exception {
//        when(userService.getCurrentUser()).thenReturn(user);
//        when(userService.getAllUsers()).thenReturn(userList);
//
//        mockMvc.perform(get("/profile"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("profile"))
//                .andExpect(model().attributeExists("user"))
//                .andExpect(model().attributeExists("users"));
//
//        verify(userService, times(1)).getCurrentUser();
//        verify(userService, times(1)).getAllUsers();
//    }

    @Test
    void testUpdateUser() throws Exception {
        int userId = 1;
        String email = "newemail@example.com";
        String telNumber = "987654321";

        mockMvc.perform(post("/profile/updateUser")
                        .param("userId", String.valueOf(userId))
                        .param("email", email)
                        .param("telNumber", telNumber))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).updateUser(userId, email, telNumber);
    }

    @Test
    void testAddPoint() throws Exception {
        int userId = 1;
        int bonusPoints = 10;

        mockMvc.perform(post("/profile/addPoint")
                        .param("userId", String.valueOf(userId))
                        .param("bonusPoints", String.valueOf(bonusPoints)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).addBonusPoints(userId, bonusPoints);
    }

    @Test
    void testDeleteUser() throws Exception {
        int userId = 1;

        mockMvc.perform(post("/profile/deleteUser")
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).deleteUser(userId);
    }
}
