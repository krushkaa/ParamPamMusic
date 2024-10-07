package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Репозиторий для работы с сущностью Album.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как поиск альбома по заголовку.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    /**
     * Находит альбом по заголовку.
     *
     * @param title заголовок альбома
     * @return Optional, содержащий найденный альбом, если он существует
     */
    Optional<Album> findByTitle(String title);
}