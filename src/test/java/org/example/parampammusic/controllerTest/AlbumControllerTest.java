package org.example.parampammusic.controllerTest;

import org.example.parampammusic.controller.AlbumController;
import org.example.parampammusic.entity.Album;
import org.example.parampammusic.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AlbumControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlbumService albumService;

    @InjectMocks
    private AlbumController albumController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(albumController).build();
    }

//    @Test
//    void testGetAllAlbums() throws Exception {
//
//        Album album1 = new Album();
//        album1.setId(1);
//        album1.setTitle("Album One");
//        album1.setReleaseDate(LocalDate.of(2021, 1, 1));
//
//        Album album2 = new Album();
//        album2.setId(2);
//        album2.setTitle("Album Two");
//        album2.setReleaseDate(LocalDate.of(2022, 2, 2));
//
//        album1 = new Album();
//        album2 = new Album();
//
//        when(albumService.getAllAlbums()).thenReturn(Arrays.asList(album1, album2));
//
//        mockMvc.perform(get("/album"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("album"))
//                .andExpect(model().attributeExists("album"))
//                .andExpect(model().attribute("album", Arrays.asList(album1, album2)));
//
//        verify(albumService, times(1)).getAllAlbums();
//    }

    @Test
    void testAddAlbum() throws Exception {

        Album album1 = new Album();
        album1.setId(1);
        album1.setTitle("Album One");
        album1.setReleaseDate(LocalDate.of(2021, 1, 1));

        mockMvc.perform(post("/album/addAlbum")
                        .flashAttr("album", album1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/album"));

        verify(albumService, times(1)).addAlbum(album1);
    }

    @Test
    void testUpdateAlbum() throws Exception {

        Album album1 = new Album();
        album1.setId(1);
        album1.setTitle("Album One");
        album1.setReleaseDate(LocalDate.of(2021, 1, 1));

        LocalDate newReleaseDate = LocalDate.now();

        mockMvc.perform(post("/album/updateAlbum/{id}", album1.getId())
                        .flashAttr("album", album1)
                        .param("releaseDate", newReleaseDate.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/album"));

        verify(albumService, times(1)).updateAlbum(album1, newReleaseDate);
    }

    @Test
    void testDeleteAlbum() throws Exception {
        int albumId = 1;

        mockMvc.perform(post("/album/deleteAlbum/{id}", albumId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/album"));

        verify(albumService, times(1)).deleteAlbum(albumId);
    }
}
