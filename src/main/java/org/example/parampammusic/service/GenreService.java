package org.example.parampammusic.service;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.repository.AlbumRepository;
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

    public Genre getGenreById(int genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Genre with id " + genreId + " not found."));
    }
}
