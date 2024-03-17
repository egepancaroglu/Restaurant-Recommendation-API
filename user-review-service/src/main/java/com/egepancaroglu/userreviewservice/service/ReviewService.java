package com.egepancaroglu.userreviewservice.service;

import com.egepancaroglu.userreviewservice.dto.ReviewDTO;
import com.egepancaroglu.userreviewservice.request.review.ReviewSaveRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewUpdateRequest;

import java.util.List;

/**
 * @author egepancaroglu
 */

public interface ReviewService {

    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(Long id);

    List<ReviewDTO> getReviewsByRestaurantId(String restaurantId);

    ReviewDTO saveReview(ReviewSaveRequest request);

    ReviewDTO updateReview(ReviewUpdateRequest request);

    void deleteReviewById(Long id);


}
