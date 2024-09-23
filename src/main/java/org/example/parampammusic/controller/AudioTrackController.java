package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.service.AlbumService;
import org.example.parampammusic.service.ArtistService;
import org.example.parampammusic.service.AudioTrackService;
import org.example.parampammusic.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AudioTrackController {

    private final AudioTrackService audioTrackService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;

    public AudioTrackController(AudioTrackService audioTrackService, AlbumService albumService, ArtistService artistService, GenreService genreService) {
        this.audioTrackService = audioTrackService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.genreService = genreService;

    }

    @GetMapping("/track")
    public String getAllAudioTrack(Model model) {
        List<AudioTrack> audioTracks = audioTrackService.getAllAudioTrack();
        model.addAttribute("audioTrack", audioTracks);
        return "track";
    }

    @GetMapping("/track/addTrack")
    public String showAddTrackForm(Model model) {
        model.addAttribute("albums", albumService.getAllAlbums());
        model.addAttribute("artists", artistService.getAllArtist());
        model.addAttribute("genres", genreService.getAllGenres());

        return "redirect:/track";
    }

    @PostMapping("/track/addTrack")
    public String addAudioTrack(@RequestParam String title,
                                @RequestParam double price,
                                @RequestParam int albumId,
                                @RequestParam int artistId,
                                @RequestParam int genreId) {

        Album album = new Album();
        album.setId(albumId);
        album = albumService.getAlbumById(album);

        Artist artist = new Artist();
        artist.setId(artistId);
        artist = artistService.getArtistById(artist);

        Genre genre = new Genre();
        genre.setId(genreId);
        genre = genreService.getGenreById(genre);

        AudioTrack audioTrack = new AudioTrack();
        audioTrack.setTitle(title);
        audioTrack.setPrice(price);
        audioTrack.setAlbum(album);
        audioTrack.setArtist(artist);
        audioTrack.setGenre(genre);

        audioTrackService.addAudioTrack(audioTrack);

        return "redirect:/track";
    }

    @PostMapping("/track/updateAudioTrack/{id}")
    public String updateAudioTrack(
            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String genreName,
            @RequestParam String artistName,
            @RequestParam String albumTitle,
            @RequestParam double price) {

        Genre genre = genreService.findByName(genreName);
        Artist artist = artistService.findByName(artistName);
        Album album = albumService.findByTitle(albumTitle);

        audioTrackService.updateAudioTrack(id, title, genre, artist, album, price);

        return "redirect:/track";
    }


    @PostMapping("/track/deleteAudioTrack/{id}")
    public String deleteAudioTrack(@PathVariable("id") int audioTrackId) {
        audioTrackService.deleteAudioTrack(audioTrackId);
        return "redirect:/track";
    }
}