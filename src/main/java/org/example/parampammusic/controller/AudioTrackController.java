package org.example.parampammusic.controller;

import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.service.AudioTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AudioTrackController {

    private final AudioTrackService audioTrackService;

    @Autowired
    public AudioTrackController(AudioTrackService audioTrackService) {
        this.audioTrackService = audioTrackService;
    }

    @GetMapping("/track")
    public String getAllAudioTrack(Model model) {
        List<AudioTrack> audioTracks = audioTrackService.getAllAudioTrack();
        model.addAttribute("audioTrack", audioTracks);
        return "track";
    }

    @PostMapping("/addTrack")
    public String addAudioTrack(@ModelAttribute AudioTrack audioTrack) {
        audioTrackService.addAudioTrack(audioTrack);
        return "redirect:/track";
    }

    @PostMapping("/updateAudioTrack/{id}")
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

    @PostMapping("/deleteAudioTrack/{id}")
    public String deleteAudioTrack(@PathVariable("id") int audioTrackId) {
        audioTrackService.deleteAudioTrack(audioTrackId);
        return "redirect:/track";
    }
}