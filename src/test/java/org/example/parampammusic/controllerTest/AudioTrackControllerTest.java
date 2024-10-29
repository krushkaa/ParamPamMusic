package org.example.parampammusic.controllerTest;

import org.example.parampammusic.DTO.AudioTrackDTO;
import org.example.parampammusic.controller.AudioTrackController;
import org.example.parampammusic.service.AudioTrackService;
import org.example.parampammusic.service.AlbumService;
import org.example.parampammusic.service.ArtistService;
import org.example.parampammusic.service.GenreService;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Album;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AudioTrackControllerTest {

    @Mock
    private AudioTrackService audioTrackService;

    @Mock
    private AlbumService albumService;

    @Mock
    private ArtistService artistService;

    @Mock
    private GenreService genreService;

    @Mock
    private Model model;

    @InjectMocks
    private AudioTrackController audioTrackController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAudioTrack() {
        List<AudioTrack> audioTracks = Arrays.asList(new AudioTrack(), new AudioTrack());
        when(audioTrackService.getAllAudioTrack()).thenReturn(audioTracks);

        String viewName = audioTrackController.getAllAudioTrack(model);

        assertEquals("track", viewName);
        verify(model, times(1)).addAttribute("audioTrack", audioTracks);
    }

//    @Test
//    public void testShowAddTrackForm() {
//        when(albumService.getAllAlbums()).thenReturn(Arrays.asList(new Album()));
//        when(artistService.getAllArtist()).thenReturn(Arrays.asList(new Artist()));
//        when(genreService.getAllGenres()).thenReturn(Arrays.asList(new Genre()));
//
//        String viewName = audioTrackController.showAddTrackForm(model);
//
//        assertEquals("redirect:/track", viewName);
//        verify(model, times(1)).addAttribute(eq("albums"), any());
//        verify(model, times(1)).addAttribute(eq("artists"), any());
//        verify(model, times(1)).addAttribute(eq("genres"), any());
//    }
//
//    @Test
//    public void testAddAudioTrack() {
//        when(albumService.getAlbumById(any(Album.class))).thenReturn(new Album());
//        when(artistService.getArtistById(any(Artist.class))).thenReturn(new Artist());
//        when(genreService.getGenreById(any(Genre.class))).thenReturn(new Genre());
//
//        String viewName = audioTrackController.addAudioTrack("Test Track", 10.99, "First", "Artist", "Genre");
//
//        assertEquals("redirect:/track", viewName);
//        verify(audioTrackService, times(1)).addAudioTrack(any(AudioTrack.class));
//    }

    @Test
    public void testUpdateAudioTrack() {
        AudioTrackDTO audioTrackDTO = new AudioTrackDTO();
        audioTrackDTO.setTitle("Updated Track");
        audioTrackDTO.setGenreName("Rock");
        audioTrackDTO.setName("Artist Name");
        audioTrackDTO.setAlbumTitle("Album Title");
        audioTrackDTO.setPrice(12.99);

        AudioTrack existingTrack = new AudioTrack();
        when(audioTrackService.getAudioTrackById(1)).thenReturn(Optional.of(existingTrack));
        when(genreService.findByName("Rock")).thenReturn(new Genre());
        when(artistService.findByArtistName("Artist Name")).thenReturn(new Artist());
        when(albumService.findByTitle("Album Title")).thenReturn(new Album());

        String viewName = audioTrackController.updateAudioTrack(1, audioTrackDTO);

        assertEquals("redirect:/track", viewName);
        verify(audioTrackService, times(1)).updateAudioTrack(any(AudioTrack.class), any(Artist.class), any(Genre.class));
    }

    @Test
    public void testDeleteAudioTrack() {
        String viewName = audioTrackController.deleteAudioTrack(1);

        assertEquals("redirect:/track", viewName);
        verify(audioTrackService, times(1)).deleteAudioTrack(1);
    }
}

