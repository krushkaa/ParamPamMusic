package org.example.parampammusic.controller;

import org.example.parampammusic.entity.AudioTrack;
import org.example.parampammusic.entity.Review;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.service.AudioTrackService;
import org.example.parampammusic.service.ReviewService;
import org.example.parampammusic.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
/**
 * Контроллер для управления отзывами на музыкальные треки.
 */
@Controller
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final UserService userService;
    private final AudioTrackService audioTrackService;
    /**
     * Конструктор контроллера отзывов.
     *
     * @param reviewService      сервис для управления отзывами
     * @param userService        сервис для управления пользователями
     * @param audioTrackService  сервис для управления аудиотреками
     */
    public ReviewController(ReviewService reviewService,
                            UserService userService,
                            AudioTrackService audioTrackService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.audioTrackService = audioTrackService;
    }

    /**
     * Получает список отзывов для определённого аудиотрека.
     *
     * @param audioTrackId идентификатор аудиотрека
     * @param model        объект Model для передачи данных на страницу
     * @return представление страницы с отзывами
     */
    @GetMapping("/review/{audioTrackId}")
    public String getReviews(@PathVariable int audioTrackId, Model model) {
        logger.info("Получение отзывов для аудиотрека {}", audioTrackId);

        List<Review> reviews = reviewService.getReviewsForTrack(audioTrackId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("audioTrackId", audioTrackId);
        return "reviewList";
    }

    /**
     * Добавляет новый отзыв для аудиотрека.
     *
     * @param review       объект отзыва, заполненный пользователем
     * @param audioTrackId идентификатор аудиотрека
     * @param principal    объект, представляющий текущего пользователя
     * @return перенаправление на страницу треков или на страницу ошибки
     */
    @PostMapping("/review/add")
    public String addReview(@ModelAttribute Review review, @RequestParam int audioTrackId, Principal principal) {
        User currentUser = userService.findByLogin(principal.getName());
        logger.info("Авторизованный пользователь {}", currentUser.getLogin());

        Optional<AudioTrack> audioTrackOptional = audioTrackService.getAudioTrackById(audioTrackId);
        if (audioTrackOptional.isPresent()) {
            AudioTrack audioTrack = audioTrackOptional.get();
            review.setUser(currentUser);
            review.setAudioTrack(audioTrack);
            reviewService.addReview(review);
            logger.info("Добавлени отзыв для аудиотрека {}", audioTrackId);
            return "redirect:/track";
        } else {
            logger.warn("Не найден аудиотрек с ID {}", audioTrackId);
            return "redirect:/error";
        }
    }

    /**
     * Отображает список отзывов для аудиотрека.
     *
     * @param audioTrackId идентификатор аудиотрека
     * @param model        объект Model для передачи данных на страницу
     * @return перенаправление на страницу с отзывами
     */
    @GetMapping("reviewList")
    public String showReview(@RequestParam(name = "audioTrackId") int audioTrackId, Model model) {
        logger.info("Отображен список отзывов для трека {}", audioTrackId);

        List<Review> reviews = reviewService.getReviewsForTrack(audioTrackId);

        model.addAttribute("reviews", reviews);
        model.addAttribute("audioTrackId", audioTrackId);
        model.addAttribute("trackName", AudioTrackService.getTrackNameByAudioTrackId(audioTrackId));

        logger.debug("Получены отзывы для аудиотрека {}: {}", audioTrackId, reviews);
        return "redirect:/review/reviewList";
    }
}
