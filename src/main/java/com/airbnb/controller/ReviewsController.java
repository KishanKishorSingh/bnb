package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.ReviewsDto;
import com.airbnb.service.interfaceClass.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    // Create a new review
    @PostMapping("/create")
    public ResponseEntity<ReviewsDto> createReview(@RequestParam("property_id") Long propertyId,
                                                   @RequestBody ReviewsDto dto,
                                                   @AuthenticationPrincipal AppUser user) {
        ReviewsDto createdReview = reviewsService.createReview(propertyId, user, dto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // Get a review by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReviewsDto> getReviewById(@PathVariable Long id) {
        ReviewsDto review = reviewsService.getReviewById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // Update a review
    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewsDto> updateReview(@PathVariable Long id,
                                                   @RequestBody ReviewsDto dto) {
        ReviewsDto updatedReview = reviewsService.updateReview(id, dto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // Delete a review
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewsService.deleteReview(id);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }

    // Get reviews by property and/or user
    @GetMapping("/search")
    public ResponseEntity<List<ReviewsDto>> getReviewsByPropertyAndUser(@RequestParam(required = false) Long propertyId,
                                                                        @RequestParam(required = false) Long userId) {
        List<ReviewsDto> reviews = reviewsService.getReviewsByPropertyAndUser(propertyId, userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}

