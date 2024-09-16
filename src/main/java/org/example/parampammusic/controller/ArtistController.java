package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist")
    public String getAllArtist(Model model) {
        List<Artist> artists = artistService.getAllArtist();
        model.addAttribute("artist", artists);
        return "redirect:/artist";
    }
    @PostMapping("/addArtist")
    public String addArtist(@ModelAttribute Artist artist) {
        artistService.addArtist(artist);
        return "redirect:/artist";
    }

    @PostMapping("/updateArtist/{id}")
    public String updateArtist(@PathVariable("id") int id, @RequestParam("name") String name) {
        artistService.updateArtist(id, name);
        return "redirect:/artist";
    }

    @PostMapping("/deleteArtist/{id}")
    public String deleteArtist(@PathVariable("id") int id) {
        artistService.deleteArtist(id);
        return "redirect:/artist";
    }

}
