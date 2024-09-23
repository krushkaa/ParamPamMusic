package org.example.parampammusic.service;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.repository.AudioTrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudioTrackService {

    private static AudioTrackRepository audioTrackRepository;

    public AudioTrackService(AudioTrackRepository audioTrackRepository) {
        AudioTrackService.audioTrackRepository = audioTrackRepository;
    }

    public void addAudioTrack(AudioTrack audioTrack) {
        audioTrackRepository.save(audioTrack);
    }

    public Optional<AudioTrack> getAudioTrackById(int id) {
        return audioTrackRepository.findById(id);
    }

    public static String getTrackNameByAudioTrackId(int audioTrackId) {
        return audioTrackRepository.getAudioTrackName(audioTrackId);
    }

    public void updateAudioTrack(Integer id, String title, Genre genre, Artist artist, Album album, double price) {
        AudioTrack existingAudioTrack = audioTrackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AudioTrack not found"));

        existingAudioTrack.setTitle(title);
        existingAudioTrack.setGenre(genre);
        existingAudioTrack.setArtist(artist);
        existingAudioTrack.setAlbum(album);
        existingAudioTrack.setPrice(price);

        audioTrackRepository.save(existingAudioTrack);
    }


    public List<AudioTrack> getAllAudioTrack() {
        return audioTrackRepository.findAll();
    }

    public void deleteAudioTrack(int id) {
        audioTrackRepository.deleteById(id);
    }
}