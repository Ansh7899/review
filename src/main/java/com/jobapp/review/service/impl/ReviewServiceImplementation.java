package com.jobapp.review.service.impl;

import com.jobapp.review.model.Review;
import com.jobapp.review.repository.ReviewRepository;
import com.jobapp.review.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImplementation implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImplementation(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Boolean addReviewForCompany(Long companyId, Review review) {
        if(companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.saveAndFlush(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getSpecificReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Boolean updateReviewById(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setCompanyId(updatedReview.getCompanyId());
            review.setId(reviewId);
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setTitle(updatedReview.getTitle());
            reviewRepository.saveAndFlush(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteReviewById(Long id) {
        try {
            Optional<Review> reviewOptional = reviewRepository.findById(id);
            if (reviewOptional.isPresent()) {
                reviewRepository.deleteById(id);
                return true; // Deletion successful
            } else {
                return false; // Entity with given ID doesn't exist
            }
        } catch (Exception e) {
            return false; // Deletion unsuccessful due to exception
        }
    }

}
