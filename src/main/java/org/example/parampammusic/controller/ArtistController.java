package org.example.parampammusic.controller;

import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.service.ArtistService;
import org.example.parampammusic.util.AdminValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Контроллер для управления действиями, связанными с артистами.
 */
@Controller
public class ArtistController {

    private final ArtistService artistService;
    /**
     * Конструктор, инициализирующий ArtistController с ArtistService.
     *
     * @param artistService сервис для работы с артистами
     */
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * Отображает список всех артистов.
     *
     * @param model объект Model для передачи данных в представление
     * @return имя представления для страницы с артистами
     */
    @GetMapping("/artist")
    public String getAllArtist(Model model) {
        List<Artist> artists = artistService.getAllArtist();
        model.addAttribute("artist", artists);
        return "artist";
    }

    /**
     * Добавляет нового артиста.
     *
     * @param artist объект Artist для добавления
     * @return перенаправление на страницу с артистами
     */
    @PostMapping("/artist/addArtist")
    public String addArtist(@ModelAttribute Artist artist) {
        artistService.addArtist(artist);
        return "redirect:/artist";
    }

    /**
     * Обновляет имя артиста по его ID.
     *
     * @param id ID артиста для обновления
     * @param name новое имя артиста
     * @return перенаправление на страницу с артистами
     */
    @PostMapping("/artist/updateArtist/{id}")
    public String updateArtist(@PathVariable Integer id, @RequestParam String name) {
        artistService.updateArtist(id, name);
        return "redirect:/artist";
    }

    /**
     * Удаляет артиста по его ID.
     *
     * @param id ID артиста для удаления
     * @return перенаправление на страницу с артистами
     */
    @PostMapping("/artist/deleteArtist/{id}")
    public String deleteArtist(@PathVariable("id") int id) {
        artistService.deleteArtist(id);
        return "redirect:/artist";
    }
}
