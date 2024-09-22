package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.service.AlbumService;
import org.example.parampammusic.service.ArtistService;
import org.example.parampammusic.service.AudioTrackService;
import org.example.parampammusic.service.GenreService;
import org.example.parampammusic.util.AdminValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/track/addTrack")
    public String addAudioTrack(@ModelAttribute AudioTrack audioTrack, Model model) {
        List<Album> albums = albumService.getAllAlbums();
        List<Artist> artists = artistService.getAllArtist();
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("genres", genres);
        audioTrackService.addAudioTrack(audioTrack);
        return "redirect:/track";
    }

    @PostMapping("/track/updateAudioTrack/{id}")
    public String updateAudioTrack(@PathVariable("id") int audioTrackId, @ModelAttribute AudioTrack updatedAudioTrack) {
        AudioTrack audioTrack = audioTrackService.getAudioTrackById(audioTrackId)
                .orElseThrow(() -> new IllegalArgumentException("AudioTrack not found"));
        audioTrack.setTitle(updatedAudioTrack.getTitle());
        audioTrack.setPrice(updatedAudioTrack.getPrice());
        audioTrack.setAlbum(updatedAudioTrack.getAlbum());
        audioTrack.setArtist(updatedAudioTrack.getArtist());
        audioTrack.setGenre(updatedAudioTrack.getGenre());
        audioTrackService.updateAudioTrack(audioTrack);
        return "redirect:/track";
    }

    @PostMapping("/track/deleteAudioTrack/{id}")
    public String deleteAudioTrack(@PathVariable("id") int audioTrackId) {
        audioTrackService.deleteAudioTrack(audioTrackId);
        return "redirect:/track";
    }
}