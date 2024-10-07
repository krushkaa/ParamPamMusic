package org.example.parampammusic.controller;

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
        albumService.addAlbum(album);
        return "redirect:/album";
    }

    /**
     * Обновляет информацию об альбоме.
     *
     * @param album объект Album для обновления
     * @param releaseDate новая дата выпуска альбома
     * @return перенаправление на страницу альбомов
     */
    @PostMapping("/album/updateAlbum/{id}")
    public String updateAlbum(Album album, @RequestParam LocalDate releaseDate) {
        if (album == null) {
            return "redirect:/error";
        } else {
            albumService.updateAlbum(album, releaseDate);
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
        albumService.deleteAlbum(albumId);
        return "redirect:/album";
    }
}
