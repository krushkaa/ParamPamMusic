package org.example.parampammusic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.repository.AudioTrackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с аудиотреками.
 * Предоставляет методы для выполнения операций с аудиотреками,
 * такими как добавление, получение, обновление и удаление треков.
 */
@Service
@Transactional
public class AudioTrackService {

    private static final Logger logger = LogManager.getLogger(AudioTrackService.class);

    private static AudioTrackRepository audioTrackRepository;

    /**
     * Конструктор для инициализации AudioTrackService с заданным репозиторием аудиотреков.
     *
     * @param audioTrackRepository репозиторий для работы с аудиотреками
     */
    public AudioTrackService(AudioTrackRepository audioTrackRepository) {
        AudioTrackService.audioTrackRepository = audioTrackRepository;
    }

    /**
     * Добавляет новый аудиотрек.
     *
     * @param audioTrack аудиотрек для добавления
     */
    public void addAudioTrack(AudioTrack audioTrack) {
        audioTrackRepository.save(audioTrack);
    }

    /**
     * Получает аудиотрек по идентификатору.
     *
     * @param id идентификатор аудиотрека
     * @return найденный аудиотрек, обернутый в Optional
     */
    public Optional<AudioTrack> getAudioTrackById(int id) {
        return audioTrackRepository.findById(id);
    }

    /**
     * Получает название трека по идентификатору аудиотрека.
     *
     * @param audioTrackId идентификатор аудиотрека
     * @return название аудиотрека
     */
    public static String getTrackNameByAudioTrackId(int audioTrackId) {
        return audioTrackRepository.getAudioTrackName(audioTrackId);
    }

    /**
     * Обновляет информацию о существующем аудиотреке.
     *
     * @param audioTrack объект аудиотрека для обновления
     * @param artist     новый артист аудиотрека
     * @param genre      новый жанр аудиотрека
     * @throws IllegalArgumentException если аудиотрек с указанным идентификатором не найден
     */
    public void updateAudioTrack(AudioTrack audioTrack, Artist artist, Genre genre) {
        AudioTrack existingAudioTrack = audioTrackRepository.findById(audioTrack.getId())
                .orElseThrow(() -> new IllegalArgumentException("AudioTrack not found"));

        existingAudioTrack.setTitle(audioTrack.getTitle());
        existingAudioTrack.setGenre(audioTrack.getGenre());
        existingAudioTrack.setArtist(audioTrack.getArtist());
        existingAudioTrack.setAlbum(audioTrack.getAlbum());
        existingAudioTrack.setPrice(audioTrack.getPrice());

        audioTrackRepository.save(existingAudioTrack);

    }

    /**
     * Получает список всех аудиотреков.
     *
     * @return список всех аудиотреков
     */
    public List<AudioTrack> getAllAudioTrack() {
        return audioTrackRepository.findAll();
    }

    /**
     * Удаляет аудиотрек по идентификатору.
     *
     * @param id идентификатор аудиотрека для удаления
     */
    public void deleteAudioTrack(int id) {
        audioTrackRepository.deleteById(id);
    }
}
