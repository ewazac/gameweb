package com.example.demo.services;

import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Review;
import com.example.demo.model.dao.User;
import com.example.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final FileService fileService;

    public Review save(Review review) {
        User currentUser = userService.getCurrentUser();
        review.setUserId(currentUser.getId());
        Review save = reviewRepository.save(review);
        userService.addPoint();
        return save;
    }

    public Review addReviewImage(MultipartFile multipartFile, String reviewId) throws IOException {
        Review review = findReviewById(reviewId);
        review.setImageUrl(review.getId() + ".png");
        fileService.uploadFileToReview(multipartFile.getBytes(), review.getId() + ".png");
        return reviewRepository.save(review);
    }


    public Review update(Review review, String reviewId) {
        Review reviewDb = findReviewById(reviewId);
        reviewDb.setDescription(review.getDescription());
        reviewDb.setStars(review.getStars());
        reviewDb.setTitle(review.getTitle());
        return reviewRepository.save(reviewDb);
    }

    public List<Review> getAllReviewsForUser() {
        String currentUser = userService.getCurrentUser().getId();
        return reviewRepository.findByUserIdAndAcceptedIsTrue(currentUser);
    }

    public List<Review> findAll() {
        return reviewRepository.findByAcceptedIsTrue();
    }

    public void deleteById(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public Review findReviewById(String reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review doesn't exist"));
    }

    public List<Review> findReviewsByGameId(String gameId) {
        return reviewRepository.findByGameIdAndAcceptedIsTrue(gameId);
    }

    public void acceptReview(String id) {
        Review review = findReviewById(id);
        review.setAccepted(!review.isAccepted());
        reviewRepository.save(review);
    }
}
