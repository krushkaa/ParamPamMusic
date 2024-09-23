package org.example.parampammusic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Album;
import org.example.parampammusic.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private static final Logger logger = LogManager.getLogger(AlbumService.class);

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Album album) {
        logger.info("Getting album by id: {}", album.getId());
        return albumRepository.findById(album.getId())
                .orElseThrow(() -> new IllegalArgumentException("Album with id " + album.getId() + " not found."));
    }

    public Album findByTitle(String title) {
        return albumRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Album not found: " + title));
    }

    public void addAlbum(Album album) {
        albumRepository.save(album);
    }

    public void updateAlbum(Album album, LocalDate releaseDate) {
        Album existingAlbum = getAlbumById(album);
        logger.info("Updating album: {}", existingAlbum.getId());
        existingAlbum.setTitle(album.getTitle());
        existingAlbum.setReleaseDate(releaseDate);
        albumRepository.save(existingAlbum);
    }

    public void deleteAlbum(int albumId) {
        Optional<Album> existingAlbum = albumRepository.findById(albumId);
        logger.info("Deleting album {}", albumId);
        if (existingAlbum.isPresent()) {
            albumRepository.delete(existingAlbum.get());
        } else {
            throw new IllegalArgumentException("Album with id " + albumId + " not found for delete.");
        }
    }
}
