package org.example.parampammusic.controller;

import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myTrack")
    public ResponseEntity<List<AudioTrack>> getPurchasedTracks(@PathVariable int userId) {
        List<AudioTrack> purchasedTracks = userService.getPurchasedTracksForUser(userId);
        if (purchasedTracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(purchasedTracks);
        }
    }
}
