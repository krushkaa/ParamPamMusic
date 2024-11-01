package org.example.parampammusic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Role;
import org.example.parampammusic.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Сервис для управления ролями пользователей.
 * Предоставляет методы для получения ролей, их удаления и получения списка всех ролей.
 */
@Service
public class RoleService {

    private static final Logger logger = LogManager.getLogger(RoleService.class);

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
        logger.info("Запрос на получение роли по id: {}", roleId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            logger.info("Роль с id {} найдена: {}", roleId, role.get().getRoleName());
        } else {
            logger.warn("Роль с id {} не найдена", roleId);
        }
        return role;
    }

    /**
     * Получает список всех ролей.
     *
     * @return список всех ролей
     */
    public List<Role> getAllRoles() {
        logger.info("Запрос на получение всех ролей");
        List<Role> roles = roleRepository.findAll();
        logger.info("Найдено {} ролей", roles.size());
        return roles;
    }

    /**
     * Удаляет роль по ее идентификатору.
     *
     * @param id идентификатор роли для удаления
     */
    public void deleteRole(int id) {
        logger.info("Запрос на удаление роли с id: {}", id);
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            logger.info("Роль с id {} успешно удалена", id);
        } else {
            logger.warn("Роль с id {} не найдена для удаления", id);
        }
    }
}
