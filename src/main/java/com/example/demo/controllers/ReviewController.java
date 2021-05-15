package com.example.demo.controllers;

import com.example.demo.mapper.ReviewMapper;
import com.example.demo.model.dao.Review;
import com.example.demo.model.dto.ReviewDto;
import com.example.demo.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
@CrossOrigin(origins = "https://gameweb2.herokuapp.com", allowCredentials = "true")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;


    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ReviewDto save(ReviewDto reviewDto) {
        return reviewMapper.toDto(reviewService.save(reviewMapper.toDao(reviewDto)));
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ReviewDto getReviewById(@PathVariable String id) {
        return reviewMapper.toDto(reviewService.findReviewById(id));
    }

    @PatchMapping("/accepted/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void acceptReview(@PathVariable String id) {
        reviewService.acceptReview(id);
    }

    @PatchMapping(value = "/addImage/{id}")
    @PreAuthorize("isAuthenticated()")
    public Review addReviewImage(MultipartFile multipartFile, @PathVariable String id) throws IOException {
        return reviewService.addReviewImage(multipartFile, id);
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public List<ReviewDto> getReviews() {
        return reviewMapper.toListDto(reviewService.findAll());
    }

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ReviewDto updateReview(@RequestBody ReviewDto reviewDto, @PathVariable String id) {
        return reviewMapper.toDto(reviewService.update(reviewMapper.toDao(reviewDto), id));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteReviewById(@PathVariable String id) {
        reviewService.deleteById(id);
    }

    @GetMapping("/game/{id}")
    @PreAuthorize("isAuthenticated()")
    public List<Review> getReviewsByGame(@PathVariable String id) {
        return reviewService.findReviewsByGameId(id);
    }

}
