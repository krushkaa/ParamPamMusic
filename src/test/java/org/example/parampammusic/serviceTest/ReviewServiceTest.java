package org.example.parampammusic.serviceTest;

import org.example.parampammusic.entity.Review;
import org.example.parampammusic.repository.ReviewRepository;
import org.example.parampammusic.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addReview_shouldSaveReviewWithCurrentDate() {

        Review review = new Review();
        review.setComment("Amazing audio quality");

        reviewService.addReview(review);

        assertEquals(LocalDate.now(), review.getReviewDate());
        verify(reviewRepository, times(1)).save(review);
    }
}