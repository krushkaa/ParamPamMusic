package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Репозиторий для работы с сущностью Artist.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как поиск артиста по имени.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    /**
     * Находит артиста по имени.
     *
     * @param name имя артиста
     * @return Optional, содержащий найденного артиста, если он существует
     */
    Optional<Artist> findByName(String name);
}

