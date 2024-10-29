package org.example.parampammusic.serviceTest;

import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.repository.ArtistRepository;
import org.example.parampammusic.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    public ArtistServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllArtists() {
        when(artistRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(0, artistService.getAllArtist().size());
    }

    @Test
    void testAddArtist() {
        Artist artist = new Artist();

        artistService.addArtist(artist);

        verify(artistRepository, times(1)).save(artist);
    }

    @Test
    void testGetArtistById() {
        Artist artist = new Artist();
        artist.setId(1);

        when(artistRepository.findById(1)).thenReturn(Optional.of(artist));

        assertEquals(artist, artistService.getArtistById(artist));
    }

    @Test
    void testUpdateArtist() {
        Artist existingArtist = new Artist();
        existingArtist.setId(1);
        existingArtist.setName("Old Name");

        when(artistRepository.findById(1)).thenReturn(Optional.of(existingArtist));

        artistService.updateArtist(1, "New Name");

        assertEquals("New Name", existingArtist.getName());
        verify(artistRepository, times(1)).save(existingArtist);
    }

    @Test
    void testDeleteArtist() {
        int artistId = 1;

        artistService.deleteArtist(artistId);

        verify(artistRepository, times(1)).deleteById(artistId);
    }

    @Test
    void testFindByArtistName() {
        Artist artist = new Artist();
        artist.setName("Test Artist");

        when(artistRepository.findByName("Test Artist")).thenReturn(Optional.of(artist));

        assertEquals(artist, artistService.findByArtistName("Test Artist"));
    }
}
