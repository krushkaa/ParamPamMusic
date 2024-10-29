package org.example.parampammusic.repository;

import org.example.parampammusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как поиск пользователя по логину и получение всех пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Находит пользователя по логину.
     *
     * @param login логин пользователя
     * @return найденный пользователь
     */
    User findByLogin(String login);
}