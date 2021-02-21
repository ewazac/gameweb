package com.example.demo.services;

import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Review;
import com.example.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public Review update(Review review, String reviewId) {
        Review reviewDb = findReviewById(reviewId);
        reviewDb.setDescription(review.getDescription());
        reviewDb.setStars(review.getStars());
        reviewDb.setTitle(review.getTitle());
        return reviewRepository.save(reviewDb);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public void deleteById(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public Review findReviewById(String reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review doesn't exist"));
    }

    public List<Review> findReviewsByGameId(String gameId) {
        return reviewRepository.findByGameId(gameId);
    }

}
