package org.example.parampammusic.service;

import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.repository.AudioTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudioTrackService {

    private final AudioTrackRepository audioTrackRepository;

    @Autowired
    public AudioTrackService(AudioTrackRepository audioTrackRepository) {
        this.audioTrackRepository = audioTrackRepository;
    }

    public void addAudioTrack(AudioTrack audioTrack) {
        audioTrackRepository.save(audioTrack);
    }

    public Optional<AudioTrack> getAudioTrackById(int id) {
        return audioTrackRepository.findById(id);
    }

    public void updateAudioTrack(AudioTrack audioTrack) {
        audioTrackRepository.save(audioTrack);
    }

    public List<AudioTrack> getAllAudioTrack() {
        return audioTrackRepository.findAll();
    }

    public void deleteAudioTrack(int id) {
        audioTrackRepository.deleteById(id);
    }
}