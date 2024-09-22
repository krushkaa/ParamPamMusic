package org.example.parampammusic.service;

import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtist() {
        return artistRepository.findAll();
    }

    public void addArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public Artist getArtistById(int id) {
        return artistRepository.findById(id).orElse(null);
    }

    public void updateArtist(int id, String name) {
        Artist artist = artistRepository.findById(id).orElse(null);
        if (artist != null) {
            artist.setName(name);
            artistRepository.save(artist);
        }
    }

    public void deleteArtist(int id) {
        artistRepository.deleteById(id);
    }
}
