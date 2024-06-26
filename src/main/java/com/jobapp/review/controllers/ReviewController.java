package com.jobapp.review.controllers;

import com.jobapp.review.model.Review;
import com.jobapp.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    public ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        List<Review> reviewList = reviewService.findAllReviews(companyId);
        if (!reviewList.isEmpty())
            return new ResponseEntity<>(reviewList,
                HttpStatus.OK);
        return new ResponseEntity<>(null,
                HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestBody Review review) {
        Boolean reviewAdded = reviewService.addReviewForCompany(companyId, review);
        if (reviewAdded)
            return new ResponseEntity<>("Review added successfully",
                HttpStatus.CREATED);
        return new ResponseEntity<>("Review could not be added",
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getSpecificReview(reviewId);
        if (review != null)
            return new ResponseEntity<>(review,
                    HttpStatus.OK);
        return new ResponseEntity<>(null,
                HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review) {
        Boolean isUpdated = reviewService.updateReviewById(reviewId, review);
        if (isUpdated)
            return new ResponseEntity<>("Review Updated successfully",
                HttpStatus.OK);
        return new ResponseEntity<>("Review could not be updated",
                HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        Boolean isDeleted = reviewService.deleteReviewById(reviewId);
        if (isDeleted)
            return new ResponseEntity<>("Review Deleted successfully",
                    HttpStatus.OK);
        return new ResponseEntity<>("Review could not be deleted",
                HttpStatus.NOT_FOUND);
    }
}
