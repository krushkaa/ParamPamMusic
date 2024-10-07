package org.example.parampammusic.controller;

import org.example.parampammusic.DTO.AudioTrackDTO;
import org.example.parampammusic.entity.Album;
import org.example.parampammusic.entity.Artist;
import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Genre;
import org.example.parampammusic.service.AlbumService;
import org.example.parampammusic.service.ArtistService;
import org.example.parampammusic.service.AudioTrackService;
import org.example.parampammusic.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления действиями, связанными с аудиотреками.
 */
@Controller
public class AudioTrackController {

    private final AudioTrackService audioTrackService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;

    /**
     * Конструктор для инициализации зависимостей.
     *
     * @param audioTrackService сервис для работы с аудиотреками
     * @param albumService      сервис для работы с альбомами
     * @param artistService     сервис для работы с артистами
     * @param genreService      сервис для работы с жанрами
     */
    public AudioTrackController(AudioTrackService audioTrackService, AlbumService albumService, ArtistService artistService, GenreService genreService) {
        this.audioTrackService = audioTrackService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.genreService = genreService;
    }

    /**
     * Отображает список всех аудиотреков.
     *
     * @param model объект Model для передачи данных в представление
     * @return имя представления для страницы с аудиотреками
     */
    @GetMapping("/track")
    public String getAllAudioTrack(Model model) {
        List<AudioTrack> audioTracks = audioTrackService.getAllAudioTrack();
        model.addAttribute("audioTrack", audioTracks);
        return "track";
    }

    /**
     * Отображает форму для добавления нового аудиотрека.
     *
     * @param model объект Model для передачи данных в представление
     * @return перенаправление на страницу с треками
     */
    @GetMapping("/track/addTrack")
    public String showAddTrackForm(Model model) {
        model.addAttribute("albums", albumService.getAllAlbums());
        model.addAttribute("artists", artistService.getAllArtist());
        model.addAttribute("genres", genreService.getAllGenres());

        return "redirect:/track";
    }

    /**
     * Добавляет новый аудиотрек.
     *
     * @param title    название трека
     * @param price    цена трека
     * @param albumId  ID альбома
     * @param artistId ID артиста
     * @param genreId  ID жанра
     * @return перенаправление на страницу с треками
     */
    @PostMapping("/track/addTrack")
    public String addAudioTrack(@RequestParam String title,
                                @RequestParam double price,
                                @RequestParam int albumId,
                                @RequestParam int artistId,
                                @RequestParam int genreId) {

        Album album = new Album();
        album.setId(albumId);
        album = albumService.getAlbumById(album);

        Artist artist = new Artist();
        artist.setId(artistId);
        artist = artistService.getArtistById(artist);

        Genre genre = new Genre();
        genre.setId(genreId);
        genre = genreService.getGenreById(genre);

        AudioTrack audioTrack = new AudioTrack();
        audioTrack.setTitle(title);
        audioTrack.setPrice(price);
        audioTrack.setAlbum(album);
        audioTrack.setArtist(artist);
        audioTrack.setGenre(genre);

        audioTrackService.addAudioTrack(audioTrack);

        return "redirect:/track";
    }

    /**
     * Обновляет аудиотрек по его ID.
     *
     * @param id            ID трека для обновления
     * @param audioTrackDTO объект DTO с информацией об аудиотреке
     * @return перенаправление на страницу с треками
     */
    @PostMapping("/track/updateAudioTrack/{id}")
    public String updateAudioTrack(@PathVariable Integer id, @ModelAttribute AudioTrackDTO audioTrackDTO) {
        Optional<AudioTrack> optionalAudioTrack = audioTrackService.getAudioTrackById(id);
        if (optionalAudioTrack.isPresent()) {
            AudioTrack audioTrack = optionalAudioTrack.get();

            Genre genre = genreService.findByName(audioTrackDTO.getGenreName());
            Artist artist = artistService.findByArtistName(audioTrackDTO.getName());
            Album album = albumService.findByTitle(audioTrackDTO.getAlbumTitle());

            audioTrack.setTitle(audioTrackDTO.getTitle());
            audioTrack.setGenre(genre);
            audioTrack.setArtist(artist);
            audioTrack.setAlbum(album);
            audioTrack.setPrice(audioTrackDTO.getPrice());

            audioTrackService.updateAudioTrack(audioTrack, artist, genre);


            return "redirect:/track";
        } else {
            return "redirect:/track?error=notfound";
        }
    }

    /**
     * Удаляет аудиотрек по его ID.
     *
     * @param audioTrackId ID трека для удаления
     * @return перенаправление на страницу с треками
     */
    @PostMapping("/track/deleteAudioTrack/{id}")
    public String deleteAudioTrack(@PathVariable("id") int audioTrackId) {
        audioTrackService.deleteAudioTrack(audioTrackId);
        return "redirect:/track";
    }
}