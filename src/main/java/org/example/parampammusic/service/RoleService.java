package org.example.parampammusic.service;

import org.example.parampammusic.entity.Role;
import org.example.parampammusic.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Сервис для управления ролями пользователей.
 * Предоставляет методы для получения ролей, их удаления и получения списка всех ролей.
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Конструктор для инициализации RoleService с заданным репозиторием.
     *
     * @param roleRepository репозиторий для работы с ролями
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Получает роль по ее идентификатору.
     *
     * @param roleId идентификатор роли
     * @return объект роли, если найден, иначе пустой Optional
     */
    public Optional<Role> getRoleById(int roleId) {
        return roleRepository.findById(roleId);
    }

    /**
     * Получает список всех ролей.
     *
     * @return список всех ролей
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Удаляет роль по ее идентификатору.
     *
     * @param id идентификатор роли для удаления
     */
    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
