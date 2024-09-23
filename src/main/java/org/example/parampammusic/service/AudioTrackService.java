package org.example.parampammusic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Album;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.logger.InfoLoggingStrategy;
import org.example.parampammusic.logger.LoggerContext;
import org.example.parampammusic.repository.AudioTrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudioTrackService {

    private final LoggerContext loggerContext;

    private static final Logger logger = LogManager.getLogger(AudioTrackService.class);

    private static AudioTrackRepository audioTrackRepository;

    public AudioTrackService(AudioTrackRepository audioTrackRepository) {
        AudioTrackService.audioTrackRepository = audioTrackRepository;
        this.loggerContext = new LoggerContext(new InfoLoggingStrategy());
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
        loggerContext.log("Updating track with id " + id);
        try {
            AudioTrack existingAudioTrack = audioTrackRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("AudioTrack not found"));

            existingAudioTrack.setTitle(title);
            existingAudioTrack.setGenre(genre);
            existingAudioTrack.setArtist(artist);
            existingAudioTrack.setAlbum(album);
            existingAudioTrack.setPrice(price);

            audioTrackRepository.save(existingAudioTrack);
            loggerContext.log("Track updated: " + title + " - " + artist.getName() + " - " + genre.getName());
        } catch (Exception e) {
            logger.error("Error updating track with id {}: {}", id, e.getMessage());
        }
    }

    public List<AudioTrack> getAllAudioTrack() {
        return audioTrackRepository.findAll();
    }

    public void deleteAudioTrack(int id) {
        audioTrackRepository.deleteById(id);
    }
}