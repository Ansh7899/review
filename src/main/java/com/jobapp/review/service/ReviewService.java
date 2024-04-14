package com.jobapp.review.service;

import com.jobapp.review.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviews(Long companyId);

    Boolean addReviewForCompany(Long companyId, Review review);

    Review getSpecificReview(Long reviewId);

    Boolean updateReviewById(Long reviewId, Review review);

    Boolean deleteReviewById(Long reviewId);
}
