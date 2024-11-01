package org.example.parampammusic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.service.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления действиями, связанными с артистами.
 */
@Controller
public class ArtistController {

    private static final Logger logger = LogManager.getLogger(ArtistController.class);

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
        logger.info("Загружен список артистов: {} артистов найдено", artists.size());
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
        try {
            artistService.addArtist(artist);
            logger.info("Добавлен новый артист: {}", artist.getName());
        } catch (Exception e) {
            logger.error("Ошибка при добавлении артиста '{}': {}", artist.getName(), e.getMessage());
        }
        return "redirect:/artist";
    }

    /**
     * Обновляет имя артиста по его ID.
     *
     * @param id   ID артиста для обновления
     * @param name новое имя артиста
     * @return перенаправление на страницу с артистами
     */
    @PostMapping("/artist/updateArtist/{id}")
    public String updateArtist(@PathVariable Integer id, @RequestParam String name) {
        try {
            artistService.updateArtist(id, name);
            logger.info("Обновлено имя артиста с ID {}: новое имя - '{}'", id, name);
        } catch (Exception e) {
            logger.error("Ошибка при обновлении имени артиста с ID {}: {}", id, e.getMessage());
        }
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
        try {
            artistService.deleteArtist(id);
            logger.info("Удален артист с ID {}", id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении артиста с ID {}: {}", id, e.getMessage());
        }
        return "redirect:/artist";
    }
}
