package com.jobapp.review.repository;

import com.jobapp.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//    Magic of spring-boot
//    spring-boot will automatically break down this method name and provide us this functionality
//    at runtime by looking into Review entity
//    this particular method refers to the SQL Query SELECT * from review where company_id = ?
    List<Review> findByCompanyId(Long companyId);
}
