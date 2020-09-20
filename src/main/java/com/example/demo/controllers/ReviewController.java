package com.example.demo.controllers;


import com.example.demo.model.AppUser;
import com.example.demo.model.Game;
import com.example.demo.model.Review;
import com.example.demo.model.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    ReviewRepository reviewRepository;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .title(reviewDTO.getTitle())
                .description(reviewDTO.getDescription())
                .stars(reviewDTO.getStars())
                .game(reviewDTO.getGame())
                .id(UUID.randomUUID().toString()).build();
        reviewRepository.save(review);
    }

    @GetMapping(value = "/{game}")
    public List<Review> getReviewsByGame(@PathVariable String game)
    {
        return reviewRepository.findReviewByGame(game);
    }










    @GetMapping
    public List<Review> getReviews() {return reviewRepository.findAll();}






}
