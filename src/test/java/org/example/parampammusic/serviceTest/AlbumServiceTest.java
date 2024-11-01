package org.example.parampammusic.serviceTest;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.repository.AlbumRepository;
import org.example.parampammusic.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    private Album album;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        album = new Album();
        album.setId(1);
        album.setTitle("Test Album");
        album.setReleaseDate(LocalDate.now());
    }

    @Test
    void testGetAllAlbums() {
        when(albumRepository.findAll()).thenReturn(Collections.singletonList(album));

        var albums = albumService.getAllAlbums();

        assertFalse(albums.isEmpty());
        assertEquals(1, albums.size());
        assertEquals("Test Album", albums.get(0).getTitle());
        verify(albumRepository, times(1)).findAll();
    }

    @Test
    void testGetAlbumById_AlbumFound() {
        when(albumRepository.findById(album.getId())).thenReturn(Optional.of(album));

        Album foundAlbum = albumService.getAlbumById(album);

        assertNotNull(foundAlbum);
        assertEquals("Test Album", foundAlbum.getTitle());
        verify(albumRepository, times(1)).findById(album.getId());
    }

    @Test
    void testGetAlbumById_AlbumNotFound() {
        when(albumRepository.findById(album.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                albumService.getAlbumById(album)
        );

        assertEquals("Album with id 1 not found.", exception.getMessage());
        verify(albumRepository, times(1)).findById(album.getId());
    }

    @Test
    void testFindByTitle_AlbumFound() {
        when(albumRepository.findByTitle(album.getTitle())).thenReturn(Optional.of(album));

        Album foundAlbum = albumService.findByTitle("Test Album");

        assertNotNull(foundAlbum);
        assertEquals("Test Album", foundAlbum.getTitle());
        verify(albumRepository, times(1)).findByTitle("Test Album");
    }

    @Test
    void testFindByTitle_AlbumNotFound() {
        when(albumRepository.findByTitle("Test Album")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                albumService.findByTitle("Test Album")
        );

        assertEquals("Album not found: Test Album", exception.getMessage());
        verify(albumRepository, times(1)).findByTitle("Test Album");
    }

    @Test
    void testAddAlbum() {
        albumService.addAlbum(album);

        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void testUpdateAlbum_AlbumFound() {
        when(albumRepository.findById(album.getId())).thenReturn(Optional.of(album));
        LocalDate newReleaseDate = LocalDate.now().plusDays(1);

        albumService.updateAlbum(album, newReleaseDate);

        assertEquals("Test Album", album.getTitle());
        assertEquals(newReleaseDate, album.getReleaseDate());
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void testDeleteAlbum_AlbumExists() {
        when(albumRepository.findById(album.getId())).thenReturn(Optional.of(album));

        albumService.deleteAlbum(album.getId());

        verify(albumRepository, times(1)).delete(album);
    }

    @Test
    void testDeleteAlbum_AlbumNotFound() {
        when(albumRepository.findById(album.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                albumService.deleteAlbum(album.getId())
        );

        assertEquals("Album with id 1 not found for delete.", exception.getMessage());
        verify(albumRepository, times(0)).delete(any(Album.class));
    }
}
