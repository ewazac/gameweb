package com.example.demo.controllers;

import com.example.demo.mapper.ReviewMapper;
import com.example.demo.model.dao.Review;
import com.example.demo.model.dto.ReviewDto;
import com.example.demo.services.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
@CrossOrigin(origins = "https://gameweb.projektstudencki.pl", allowCredentials = "true")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;


    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Add review to game")
    public ReviewDto save(ReviewDto reviewDto) {
        return reviewMapper.toDto(reviewService.save(reviewMapper.toDao(reviewDto)));
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Get review by id")
    public ReviewDto getReviewById(@PathVariable String id) {
        return reviewMapper.toDto(reviewService.findReviewById(id));
    }

    @PatchMapping("/accepted/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Method to accept reviews by administrator. Only reviews that are accepted by administrator are displayed for users")
    public void acceptReview(@PathVariable String id) {
        reviewService.acceptReview(id);
    }

    @PatchMapping(value = "/addImage/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Add image to review")
    public Review addReviewImage(MultipartFile multipartFile, @PathVariable String id) throws IOException {
        return reviewService.addReviewImage(multipartFile, id);
    }

    @GetMapping("/unaccepted")
    @ApiOperation(value = "Returns a list with unaccepted reviews")
    public List<Review> getAllUnacceptedReviews() {
        return reviewService.getAllUnacceptedReviews();
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Get all reviews")
    public List<ReviewDto> getReviews() {
        return reviewMapper.toListDto(reviewService.findAll());
    }

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Update review")
    public ReviewDto updateReview(@RequestBody ReviewDto reviewDto, @PathVariable String id) {
        return reviewMapper.toDto(reviewService.update(reviewMapper.toDao(reviewDto), id));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Delete review by id")
    public void deleteReviewById(@PathVariable String id) {
        reviewService.deleteById(id);
    }

    @GetMapping("/game/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Get all reviews for game")
    public List<Review> getReviewsByGame(@PathVariable String id) {
        return reviewService.findReviewsByGameId(id);
    }

}
