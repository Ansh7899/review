package com.jobapp.review.service.impl;

import com.jobapp.review.model.Company;
import com.jobapp.review.model.Review;
import com.jobapp.review.repository.ReviewRepository;
import com.jobapp.review.service.CompanyService;
import com.jobapp.review.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImplementation(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> findAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Boolean addReviewForCompany(Long companyId, Review review) {
        Company company = companyService.findCompanyById(companyId);
        if(company != null) {
            review.setCompany(company);
            reviewRepository.saveAndFlush(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getSpecificReview(Long companyId, Long reviewId) {
        List<Review> reviewList = findAllReviews(companyId);
        return reviewList.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
//        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Boolean updateReviewById(Long companyId, Long reviewId, Review updatedReview) {
        if(companyService.findCompanyById(companyId) != null) {
            updatedReview.setCompany(companyService.findCompanyById(companyId));
            updatedReview.setId(reviewId);
            updatedReview.setDescription(updatedReview.getDescription());
            updatedReview.setRating(updatedReview.getRating());
            updatedReview.setTitle(updatedReview.getTitle());
            reviewRepository.saveAndFlush(updatedReview);
            return true;
        }
        return false;
    }

//    We first fetch company and specific review
//    then delete the review from the company table
//    then delete the review from the review table as well
//    Since it is a bidirectional mapping we need to do it in such way
    @Override
    public Boolean deleteReviewById(Long companyId, Long reviewId) {
        if(companyService.findCompanyById(companyId) != null &&
            reviewRepository.existsById(reviewId)){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = companyService.findCompanyById(companyId);
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(companyId, company);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

}
