package org.example.parampammusic.service;

import org.example.parampammusic.entity.Review;
import org.example.parampammusic.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsForTrack(int audioTrackId) {
        return reviewRepository.findByAudioTrackId(audioTrackId);
    }

    public void addReview(Review review) {
        review.setReviewDate(LocalDate.now());
        reviewRepository.save(review);
    }
}
