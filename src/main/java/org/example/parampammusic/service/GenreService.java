package org.example.parampammusic.service;

import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Сервис для работы с жанрами.
 * Предоставляет методы для выполнения операций с жанрами,
 * такими как получение всех жанров, поиск жанра по идентификатору и имени.
 */
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    /**
     * Конструктор для инициализации GenreService с заданным репозиторием жанров.
     *
     * @param genreRepository репозиторий для работы с жанрами
     */
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Получает список всех жанров.
     *
     * @return список всех жанров
     */
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    /**
     * Получает жанр по идентификатору.
     *
     * @param genre жанр с заданным идентификатором
     * @return найденный жанр
     * @throws IllegalArgumentException если жанр с указанным идентификатором не найден
     */
    public Genre getGenreById(Genre genre) {
        return genreRepository.findById(genre.getId())
                .orElseThrow(() -> new IllegalArgumentException("Genre with id " + genre.getId() + " not found."));
    }

    /**
     * Находит жанр по имени.
     *
     * @param name имя жанра
     * @return найденный жанр
     * @throws IllegalArgumentException если жанр с указанным именем не найден
     */
    public Genre findByName(String name) {
        return genreRepository.findByGenreName(name)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found: " + name));
    }
}
