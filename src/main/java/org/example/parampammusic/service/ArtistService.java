package org.example.parampammusic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Сервис для работы с артистами.
 * Предоставляет методы для выполнения операций с артистами,
 * таких как получение, добавление, обновление и удаление артистов.
 */
@Service
public class ArtistService {

    private static final Logger logger = LogManager.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;
    /**
     * Конструктор для инициализации ArtistService с заданным репозиторием артистов.
     *
     * @param artistRepository репозиторий для работы с артистами
     */
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }
    /**
     * Получает список всех артистов.
     *
     * @return список всех артистов
     */
    public List<Artist> getAllArtist() {
        return artistRepository.findAll();
    }
    /**
     * Добавляет нового артиста.
     *
     * @param artist артист для добавления
     */
    public void addArtist(Artist artist) {
        artistRepository.save(artist);
    }
    /**
     * Получает артиста по идентификатору.
     *
     * @param artist артист с заданным идентификатором
     * @return найденный артист
     * @throws IllegalArgumentException если артист с указанным идентификатором не найден
     */
    public Artist getArtistById(Artist artist) {
        return artistRepository.findById(artist.getId())
                .orElseThrow(() -> new IllegalArgumentException("Artist with id " + artist.getId() + " not found."));
    }
    /**
     * Обновляет информацию о существующем артисте.
     *
     * @param id идентификатор артиста
     * @param artistName новое имя артиста
     * @throws IllegalArgumentException если артист с указанным идентификатором не найден
     */
    public void updateArtist(Integer id, String artistName) {
        Artist artist = artistRepository.findById(id).orElse(null);
        if (artist != null) {
            artist.setName(artistName);
            artistRepository.save(artist);
        }
    }
    /**
     * Удаляет артиста по идентификатору.
     *
     * @param id идентификатор артиста для удаления
     * @throws IllegalArgumentException если артист с указанным идентификатором не найден
     */
    public void deleteArtist(int id) {
        artistRepository.deleteById(id);
    }
    /**
     * Находит артиста по имени.
     *
     * @param name имя артиста
     * @return найденный артист
     * @throws IllegalArgumentException если артист с указанным именем не найден
     */
    public Artist findByArtistName(String name) {
        return artistRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found: " + name));
    }
}
