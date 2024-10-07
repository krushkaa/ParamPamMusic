package org.example.parampammusic.repository;

import org.example.parampammusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Репозиторий для работы с сущностью User.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как поиск пользователя по логину и получение всех пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Сохраняет пользователя.
     *
     * @param user пользователь для сохранения
     * @return сохраненный пользователь
     */
    User save(User user);
    /**
     * Находит пользователя по логину.
     *
     * @param login логин пользователя
     * @return найденный пользователь
     */
    User findByLogin(String login);
    /**
     * Получает всех пользователей.
     *
     * @return список всех пользователей
     */
    List<User> findAll();
    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void deleteById(Integer id);
}