package org.example.parampammusic.service;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(int albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Album with id " + albumId + " not found."));
    }

    public void addAlbum(Album album) {
        albumRepository.save(album);
    }

    public void updateAlbum(Album album) {
        Optional<Album> existingAlbum = albumRepository.findById(album.getId());

        if (existingAlbum.isPresent()) {
            albumRepository.save(album);
        } else {
            throw new IllegalArgumentException("Album with id " + album.getId() + " not found for update.");
        }
    }

    public void deleteAlbum(int albumId) {
        Optional<Album> existingAlbum = albumRepository.findById(albumId);
        if (existingAlbum.isPresent()) {
            albumRepository.delete(existingAlbum.get());
        } else {
            throw new IllegalArgumentException("Album with id " + albumId + " not found for delete.");
        }
    }
}
