package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Репозиторий для работы с сущностью Genre.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как поиск жанра по имени.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    /**
     * Находит жанр по имени.
     *
     * @param genreName имя жанра
     * @return Optional, содержащий найденный жанр, если он существует
     */
    Optional<Genre> findByGenreName(String genreName);
}
