package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.service.AlbumService;
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
    public String updateAlbum(@PathVariable("id") int albumId) {
        Album album = albumService.getAlbumById(albumId);
        albumService.updateAlbum(album);
        return "redirect:/album";
    }

    @PostMapping("/album/deleteAlbum/{id}")
    public String deleteAlbum(@PathVariable("id") int albumId) {
        albumService.deleteAlbum(albumId);
        return "redirect:/album";
    }
}
