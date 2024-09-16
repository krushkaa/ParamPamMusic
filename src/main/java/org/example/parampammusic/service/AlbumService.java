package org.example.parampammusic.service;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.repository.AlbumRepository;
import org.springframework.security.core.Authentication;
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

    public Album updateAlbum(Album album, Authentication authentication) {
        Optional<Album> existingAlbum = albumRepository.findById(album.getId());

        if (existingAlbum.isPresent()) {
            if (isAdmin(authentication)) {
                return albumRepository.save(album);
            } else {
                throw new IllegalStateException("Only admins can update albums.");
            }
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
    private boolean isAdmin(Authentication authentication) {

        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
