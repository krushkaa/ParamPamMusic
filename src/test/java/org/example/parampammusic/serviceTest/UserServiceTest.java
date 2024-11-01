package org.example.parampammusic.serviceTest;

import jakarta.servlet.http.HttpSession;
import org.example.parampammusic.entity.Role;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.repository.UserRepository;
import org.example.parampammusic.service.RoleService;
import org.example.parampammusic.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password");
        Role role = new Role();
        role.setRoleId(1);
        role.setRoleName("USER");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleService.getRoleById(1)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.createUser(user, "password", role.getRoleId());

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticate_Success() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("encodedPassword");

        when(userRepository.findByLogin("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        HttpSession session = mock(HttpSession.class);

        boolean result = userService.authenticate("testuser", "password", session);

        assertTrue(result);
        verify(session).setAttribute("login", "testuser");
    }

    @Test
    void testAuthenticate_Failure() {
        when(userRepository.findByLogin("testuser")).thenReturn(null);

        HttpSession session = mock(HttpSession.class);

        boolean result = userService.authenticate("testuser", "wrongPassword", session);

        assertFalse(result);
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        User user = new User();
        user.setLogin("testuser");
        Role role = new Role();
        role.setRoleName("USER");
        user.setRole(role);
        user.setPassword("encodedPassword");

        when(userRepository.findByLogin("testuser")).thenReturn(user);

        var userDetails = userService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByLogin("nonExistentUser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("nonExistentUser"));
    }
}
