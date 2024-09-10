package org.example.parampammusic.service;

import org.example.parampammusic.entity.Role;
import org.example.parampammusic.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName) {
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    public Optional<Role> getRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
