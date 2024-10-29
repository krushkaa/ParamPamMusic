package org.example.parampammusic.serviceTest;

import org.example.parampammusic.entity.Role;
import org.example.parampammusic.repository.RoleRepository;
import org.example.parampammusic.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRoleById_shouldReturnRole_whenRoleExists() {
        int roleId = 1;
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName("ROLE_ADMIN");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.getRoleById(roleId);

        assertTrue(result.isPresent());
        assertEquals("ROLE_ADMIN", result.get().getRoleName());
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void getRoleById_shouldReturnEmpty_whenRoleDoesNotExist() {
        int roleId = 1;

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.getRoleById(roleId);

        assertFalse(result.isPresent());
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void getAllRoles_shouldReturnListOfRoles() {
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setRoleName("ROLE_USER");

        Role role2 = new Role();
        role2.setRoleId(2);
        role2.setRoleName("ROLE_ADMIN");

        List<Role> roles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("ROLE_USER", result.get(0).getRoleName());
        assertEquals("ROLE_ADMIN", result.get(1).getRoleName());
        verify(roleRepository, times(1)).findAll();
    }

//    @Test
//    void deleteRole_shouldDeleteRoleById() {
//        int roleId = 1;
//
//        roleService.deleteRole(roleId);
//
//        verify(roleRepository, times(1)).deleteById(roleId);
//    }
}
