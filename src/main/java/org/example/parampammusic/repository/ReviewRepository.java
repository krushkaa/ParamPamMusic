package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByAudioTrackId(int audioTrackId);
}
