package org.example.parampammusic.service;

import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Genre genre) {
        return genreRepository.findById(genre.getId())
                .orElseThrow(() -> new IllegalArgumentException("Genre with id " + genre.getId() + " not found."));
    }
    public Genre findByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found: " + name));
    }
}
