package org.example.parampammusic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.Album;
import org.example.parampammusic.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для управления альбомами.
 */
@Controller
public class AlbumController {

    private static final Logger logger = LogManager.getLogger(AlbumController.class);

    private final AlbumService albumService;

    /**
     * Конструктор, инициализирующий AlbumController с AlbumService.
     *
     * @param albumService сервис для работы с альбомами
     */
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * Отображает список всех альбомов.
     *
     * @param model объект Model для передачи данных в представление
     * @return имя представления для страницы альбомов
     */
    @GetMapping("/album")
    public String getAllAlbums(Model model) {
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("album", albums);
        logger.info("Получен список всех альбомов: {} альбомов загружено", albums.size());
        return "album";
    }

    /**
     * Добавляет новый альбом.
     *
     * @param album объект Album для добавления нового альбома
     * @return перенаправление на страницу альбомов
     */
    @PostMapping("/album/addAlbum")
    public String addAlbum(@ModelAttribute Album album) {
        try {
            albumService.addAlbum(album);
            logger.info("Новый альбом добавлен: {}", album.getTitle());
        } catch (Exception e) {
            logger.error("Ошибка при добавлении альбома '{}': {}", album.getTitle(), e.getMessage());
        }
        return "redirect:/album";
    }

    /**
     * Обновляет информацию об альбоме.
     *
     * @param album       объект Album для обновления
     * @param releaseDate новая дата выпуска альбома
     * @return перенаправление на страницу альбомов
     */
    @PostMapping("/album/updateAlbum/{id}")
    public String updateAlbum(Album album, @RequestParam LocalDate releaseDate) {
        if (album == null) {
            logger.warn("Попытка обновления несуществующего альбома.");
            return "redirect:/error";
        } else {
            try {
                albumService.updateAlbum(album, releaseDate);
                logger.info("Альбом с ID {} обновлен. Новое название: {}. Новая дата выпуска: {}", album.getId(), album.getTitle(), releaseDate);
            } catch (Exception e) {
                logger.error("Ошибка при обновлении альбома с ID {}: {}", album.getId(), e.getMessage());
            }
        }
        return "redirect:/album";
    }

    /**
     * Удаляет альбом по его ID.
     *
     * @param albumId ID альбома для удаления
     * @return перенаправление на страницу альбомов
     */
    @PostMapping("/album/deleteAlbum/{id}")
    public String deleteAlbum(@PathVariable("id") int albumId) {
        try {
            albumService.deleteAlbum(albumId);
            logger.info("Альбом с ID {} успешно удален", albumId);
        } catch (Exception e) {
            logger.error("Ошибка при удалении альбома с ID {}: {}", albumId, e.getMessage());
        }
        return "redirect:/album";
    }
}
