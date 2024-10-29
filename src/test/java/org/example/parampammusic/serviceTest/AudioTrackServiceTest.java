package org.example.parampammusic.serviceTest;

import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.repository.AudioTrackRepository;
import org.example.parampammusic.service.AudioTrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AudioTrackServiceTest {

    @Mock
    private AudioTrackRepository audioTrackRepository;

    @InjectMocks
    private AudioTrackService audioTrackService;

    private AudioTrack audioTrack;
    private Artist artist;
    private Genre genre;

    @BeforeEach
    void setUp() {
        audioTrack = new AudioTrack();
        audioTrack.setId(1);
        audioTrack.setTitle("Test Track");
        audioTrack.setPrice(10.99);

        artist = new Artist();
        artist.setId(1);
        artist.setName("Test Artist");

        genre = new Genre();
        genre.setId(1);
        genre.setGenreName("Test Genre");

        audioTrack.setArtist(artist);
        audioTrack.setGenre(genre);
    }

    @Test
    void testAddAudioTrack() {
        audioTrackService.addAudioTrack(audioTrack);

        verify(audioTrackRepository, times(1)).save(audioTrack);
    }

    @Test
    void testGetAudioTrackById_TrackExists() {
        when(audioTrackRepository.findById(audioTrack.getId())).thenReturn(Optional.of(audioTrack));

        Optional<AudioTrack> result = audioTrackService.getAudioTrackById(audioTrack.getId());

        assertTrue(result.isPresent());
        assertEquals(audioTrack, result.get());
    }

    @Test
    void testGetAudioTrackById_TrackDoesNotExist() {
        when(audioTrackRepository.findById(audioTrack.getId())).thenReturn(Optional.empty());

        Optional<AudioTrack> result = audioTrackService.getAudioTrackById(audioTrack.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void testGetTrackNameByAudioTrackId() {
        String trackName = "Test Track";
        when(audioTrackRepository.getAudioTrackName(audioTrack.getId())).thenReturn(trackName);

        String result = AudioTrackService.getTrackNameByAudioTrackId(audioTrack.getId());

        assertEquals(trackName, result);
        verify(audioTrackRepository, times(1)).getAudioTrackName(audioTrack.getId());
    }

    @Test
    void testUpdateAudioTrack_Success() {
        when(audioTrackRepository.findById(audioTrack.getId())).thenReturn(Optional.of(audioTrack));
        when(audioTrackRepository.save(any(AudioTrack.class))).thenAnswer(invocation -> invocation.getArgument(0));

        audioTrack.setTitle("Updated Track");
        audioTrackService.updateAudioTrack(audioTrack, artist, genre);

        verify(audioTrackRepository, times(1)).findById(audioTrack.getId());
        verify(audioTrackRepository, times(1)).save(audioTrack);
        assertEquals("Updated Track", audioTrack.getTitle());
    }

    @Test
    void testUpdateAudioTrack_TrackNotFound() {
        when(audioTrackRepository.findById(audioTrack.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                audioTrackService.updateAudioTrack(audioTrack, artist, genre)
        );
    }

    @Test
    void testGetAllAudioTrack() {
        when(audioTrackRepository.findAll()).thenReturn(List.of(audioTrack));

        List<AudioTrack> result = audioTrackService.getAllAudioTrack();

        assertEquals(1, result.size());
        assertEquals(audioTrack, result.get(0));
        verify(audioTrackRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAudioTrack() {
        audioTrackService.deleteAudioTrack(audioTrack.getId());

        verify(audioTrackRepository, times(1)).deleteById(audioTrack.getId());
    }
}
