package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Репозиторий для работы с сущностью Role.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как поиск роли по идентификатору.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Находит роль по идентификатору.
     *
     * @param roleId идентификатор роли
     * @return Optional, содержащий найденную роль, если она существует
     */
    Optional<Role> findByRoleId (int roleId);
}