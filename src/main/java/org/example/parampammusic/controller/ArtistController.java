package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.service.ArtistService;
import org.example.parampammusic.util.AdminValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArtistController {

    private final ArtistService artistService;
    private final AdminValidator adminValidator;

    public ArtistController(ArtistService artistService, AdminValidator adminValidator) {
        this.artistService = artistService;
        this.adminValidator = adminValidator;
    }

    @GetMapping("/artist")
    public String getAllArtist(Model model) {
        List<Artist> artists = artistService.getAllArtist();
        model.addAttribute("artist", artists);
        return "redirect:/artist";
    }
    @PostMapping("/addArtist")
    public String addArtist(@ModelAttribute Artist artist, Authentication authentication) {
        adminValidator.validateAdmin(authentication);
        artistService.addArtist(artist);
        return "redirect:/artist";
    }

    @PostMapping("/updateArtist/{id}")
    public String updateArtist(@PathVariable("id") int id, @RequestParam("name") String name, Authentication authentication) {
        adminValidator.validateAdmin(authentication);
        artistService.updateArtist(id, name);
        return "redirect:/artist";
    }

    @PostMapping("/deleteArtist/{id}")
    public String deleteArtist(@PathVariable("id") int id, Authentication authentication) {
        adminValidator.validateAdmin(authentication);
        artistService.deleteArtist(id);
        return "redirect:/artist";
    }

}
