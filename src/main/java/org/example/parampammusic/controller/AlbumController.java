package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/album")
    public String getAllAlbums(Model model) {
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("album", albums);
        return "album";
    }

    @PostMapping("/addAlbum")
    public String addAlbum(@ModelAttribute Album album) {
        albumService.addAlbum(album);
        return "redirect:/album";
    }

    @PostMapping("/updateAlbum/{id}")
    public String updateAlbum(@PathVariable("id") int albumId, Authentication authentication) {
        Album album = albumService.getAlbumById(albumId);
        albumService.updateAlbum(album, authentication);
        return "redirect:/album";
    }

    @PostMapping("/deleteAlbum/{id}")
    public String deleteAlbum(@PathVariable("id") int albumId) {
        albumService.deleteAlbum(albumId);
        return "redirect:/album";
    }
}
