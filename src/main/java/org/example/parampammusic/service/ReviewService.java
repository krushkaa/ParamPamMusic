package org.example.parampammusic.service;

import org.example.parampammusic.entity.Review;
import org.example.parampammusic.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
/**
 * Сервис для управления отзывами.
 * Предоставляет методы для получения отзывов по трекам и добавления новых отзывов.
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * Конструктор для инициализации ReviewService с заданным репозиторием.
     *
     * @param reviewRepository репозиторий для работы с отзывами
     */
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Получает список отзывов для заданного трека.
     *
     * @param audioTrackId идентификатор аудиотрека
     * @return список отзывов для указанного трека
     */
    public List<Review> getReviewsForTrack(int audioTrackId) {
        return reviewRepository.findByAudioTrackId(audioTrackId);
    }

    /**
     * Добавляет новый отзыв.
     *
     * @param review отзыв для добавления
     */
    public void addReview(Review review) {
        review.setReviewDate(LocalDate.now());
        reviewRepository.save(review);
    }
}
