//package org.example.parampammusic.controllerTest;
//
//import org.example.parampammusic.controller.ArtistController;
//import org.example.parampammusic.entity.Artist;
//import org.example.parampammusic.service.ArtistService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ArtistController.class)
//public class ArtistControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ArtistService artistService;
//
//    @BeforeEach
//    void setUp() {
//
//        Artist artist1 = new Artist();
//        artist1.setId(1);
//        artist1.setName("Artist 1");
//
//        Artist artist2 = new Artist();
//        artist2.setId(2);
//        artist2.setName("Artist 2");
//
//        Mockito.when(artistService.getAllArtist()).thenReturn(Arrays.asList(artist1, artist2));
//    }
//
//    @Test
//    public void testGetAllArtist() throws Exception {
//        mockMvc.perform(get("/artist"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("artist"))
//                .andExpect(model().attributeExists("artist"))
//                .andExpect(model().attribute("artist", Arrays.asList(
//                        new Artist(),
//                        new Artist()
//                )));
//    }
//
//    @Test
//    public void testAddArtist() throws Exception {
//        Artist artist = new Artist();
//
//        mockMvc.perform(post("/artist/addArtist")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("id", "3")
//                        .param("name", artist.getName()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/artist"));
//
//        Mockito.verify(artistService).addArtist(Mockito.any(Artist.class));
//    }
//
//    @Test
//    public void testUpdateArtist() throws Exception {
//        mockMvc.perform(post("/artist/updateArtist/1")
//                        .param("name", "Updated Artist"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/artist"));
//
//        Mockito.verify(artistService).updateArtist(1, "Updated Artist");
//    }
//
//    @Test
//    public void testDeleteArtist() throws Exception {
//        mockMvc.perform(post("/artist/deleteArtist/1"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/artist"));
//
//        Mockito.verify(artistService).deleteArtist(1);
//    }
//}
