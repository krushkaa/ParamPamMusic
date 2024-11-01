package org.example.parampammusic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Album;
import org.example.parampammusic.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с альбомами.
 * Предоставляет методы для выполнения операций с альбомами,
 * таких как получение, добавление, обновление и удаление альбомов.
 */
@Service
public class AlbumService {

    private static final Logger logger = LogManager.getLogger(AlbumService.class);

    private final AlbumRepository albumRepository;

    /**
     * Конструктор для инициализации AlbumService с заданным репозиторием альбомов.
     *
     * @param albumRepository репозиторий для работы с альбомами
     */
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    /**
     * Получает список всех альбомов.
     *
     * @return список всех альбомов
     */
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    /**
     * Получает альбом по идентификатору.
     *
     * @param album альбом с заданным идентификатором
     * @return найденный альбом
     * @throws IllegalArgumentException если альбом с указанным идентификатором не найден
     */
    public Album getAlbumById(Album album) {
        logger.info("Запрос на получение альбома c id: {}", album.getId());
        return albumRepository.findById(album.getId())
                .orElseThrow(() -> {
                    logger.error("Альбом с id {} не найден", album.getId());
                    return new IllegalArgumentException("Album with id " + album.getId() + " not found.");
                });
    }

    /**
     * Находит альбом по заголовку.
     *
     * @param title заголовок альбома
     * @return найденный альбом
     * @throws IllegalArgumentException если альбом с указанным заголовком не найден
     */
    public Album findByTitle(String title) {
        logger.info("Запрос на поиск альбома по заголовку: {}", title);
        return albumRepository.findByTitle(title)
                .orElseThrow(() -> {
                    logger.error("Альбом с заголовком '{}' не найден", title);
                    return new IllegalArgumentException("Album not found: " + title);
                });
    }

    /**
     * Добавляет новый альбом.
     *
     * @param album альбом для добавления
     */
    public void addAlbum(Album album) {
        logger.info("Добавление нового альбома: {}", album.getTitle());
        albumRepository.save(album);
    }

    /**
     * Обновляет существующий альбом.
     *
     * @param album       альбом с обновленной информацией
     * @param releaseDate новая дата релиза альбома
     * @throws IllegalArgumentException если альбом с указанным идентификатором не найден
     */
    public void updateAlbum(Album album, LocalDate releaseDate) {
        logger.info("Запрос на обновление альбома с id: {}", album.getId());
        Album existingAlbum = getAlbumById(album);
        logger.info("Обновление альбома: {}", existingAlbum.getId());
        existingAlbum.setTitle(album.getTitle());
        existingAlbum.setReleaseDate(releaseDate);
        albumRepository.save(existingAlbum);
    }

    /**
     * Удаляет альбом по идентификатору.
     *
     * @param albumId идентификатор альбома для удаления
     * @throws IllegalArgumentException если альбом с указанным идентификатором не найден
     */
    public void deleteAlbum(int albumId) {
        logger.info("Запрос на удаление альбома с id: {}", albumId);
        Optional<Album> existingAlbum = albumRepository.findById(albumId);
        if (existingAlbum.isPresent()) {
            albumRepository.delete(existingAlbum.get());
        } else {
            throw new IllegalArgumentException("Album with id " + albumId + " not found for delete.");
        }
    }
}
