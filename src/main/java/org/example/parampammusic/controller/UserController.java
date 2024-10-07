package org.example.parampammusic.controller;

import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
/**
 * Контроллер для управления действиями пользователей.
 */
@Controller
public class UserController {

    private final UserService userService;
    /**
     * Конструктор контроллера пользователей.
     *
     * @param userService сервис для управления пользователями
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Возвращает список приобретённых аудиотреков для указанного пользователя.
     *
     * @return ResponseEntity, содержащее список аудиотреков или код состояния 204, если треки не найдены
     */
    @GetMapping("/myTrack")
    public ResponseEntity<List<AudioTrack>> getPurchasedTracks() {
        User currentUser = userService.getCurrentUser();
        List<AudioTrack> purchasedTracks = userService.getPurchasedTracksForUser(currentUser);
        if (purchasedTracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(purchasedTracks);
        }
    }
}
