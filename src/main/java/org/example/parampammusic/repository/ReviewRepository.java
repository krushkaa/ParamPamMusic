package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Репозиторий для работы с сущностью Review.
 * Позволяет выполнять операции CRUD и дополнительные запросы,
 * такие как получение отзывов по идентификатору аудиотрека.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    /**
     * Находит отзывы по идентификатору аудиотрека.
     *
     * @param audioTrackId идентификатор аудиотрека
     * @return список отзывов, связанных с указанным аудиотреком
     */
    List<Review> findByAudioTrackId(int audioTrackId);
    void deleteByUserId(Integer userId);
}
