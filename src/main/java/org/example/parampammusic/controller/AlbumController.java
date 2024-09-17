package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Album;
import org.example.parampammusic.service.AlbumService;
import org.example.parampammusic.util.AdminValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlbumController {

    private final AlbumService albumService;
    private final AdminValidator adminValidator;

    @Autowired
    public AlbumController(AlbumService albumService, AdminValidator adminValidator) {
        this.albumService = albumService;
        this.adminValidator = adminValidator;
    }

    @GetMapping("/album")
    public String getAllAlbums(Model model) {
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("album", albums);
        return "album";
    }

    @PostMapping("/addAlbum")
    public String addAlbum(@ModelAttribute Album album, Authentication authentication) {
        adminValidator.validateAdmin(authentication);
        albumService.addAlbum(album);
        return "redirect:/album";
    }

    @PostMapping("/updateAlbum/{id}")
    public String updateAlbum(@PathVariable("id") int albumId, Authentication authentication) {
        Album album = albumService.getAlbumById(albumId);
        adminValidator.validateAdmin(authentication);
        albumService.updateAlbum(album);
        return "redirect:/album";
    }

    @PostMapping("/deleteAlbum/{id}")
    public String deleteAlbum(@PathVariable("id") int albumId, Authentication authentication) {
        adminValidator.validateAdmin(authentication);
        albumService.deleteAlbum(albumId);
        return "redirect:/album";
    }
}
