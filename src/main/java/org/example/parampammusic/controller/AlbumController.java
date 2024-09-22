package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/album")
    public String getAllAlbums(Model model) {
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("album", albums);
        return "album";
    }

    @PostMapping("/album/addAlbum")
    public String addAlbum(@ModelAttribute Album album) {
        albumService.addAlbum(album);
        return "redirect:/album";
    }

    @PostMapping("/album/updateAlbum/{id}")
    public String updateAlbum(Album album, @RequestParam LocalDate releaseDate) {
        if (album == null) {
            return "redirect:/error";
        } else {
            albumService.updateAlbum(album, releaseDate);
        }
        return "redirect:/album";
    }

    @PostMapping("/album/deleteAlbum/{id}")
    public String deleteAlbum(@PathVariable("id") int albumId) {
        albumService.deleteAlbum(albumId);
        return "redirect:/album";
    }
}
