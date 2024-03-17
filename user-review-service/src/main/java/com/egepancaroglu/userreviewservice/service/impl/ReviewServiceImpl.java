package com.egepancaroglu.userreviewservice.service.impl;

import com.egepancaroglu.userreviewservice.client.RestaurantClient;
import com.egepancaroglu.userreviewservice.dto.ReviewDTO;
import com.egepancaroglu.userreviewservice.dto.response.ReviewResponse;
import com.egepancaroglu.userreviewservice.entity.Review;
import com.egepancaroglu.userreviewservice.exception.ItemNotFoundException;
import com.egepancaroglu.userreviewservice.general.ErrorMessages;
import com.egepancaroglu.userreviewservice.mapper.ReviewMapper;
import com.egepancaroglu.userreviewservice.repository.ReviewRepository;
import com.egepancaroglu.userreviewservice.request.restaurant.RestaurantUpdateAverageScoreRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewSaveRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewUpdateRequest;
import com.egepancaroglu.userreviewservice.service.ReviewService;
import com.egepancaroglu.userreviewservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author egepancaroglu
 */

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;
    private final RestaurantClient restaurantClient;

    @Override
    public List<ReviewDTO> getAllReviews() {

        List<Review> reviewList = reviewRepository.findAll();

        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDTO reviewDTO = reviewMapper.convertToReviewDTO(review);
            reviewDTOList.add(reviewDTO);
        }

        return reviewDTOList;

    }

    @Override
    public ReviewDTO getReviewById(Long id) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));

        return reviewMapper.convertToReviewDTO(review);

    }

    @Override
    public List<ReviewDTO> getReviewsByRestaurantId(String restaurantId) {

        List<Review> reviewList = reviewRepository.findReviewsByRestaurantId(restaurantId);

        return reviewList.stream()
                .map(reviewMapper::convertToReviewDTO)
                .toList();

    }

    @Override
    public ReviewResponse saveReview(ReviewSaveRequest request) {

        Review review = reviewMapper.convertToReview(request);
        review.setUser(userService.getUserEntity(request.userId()));
        review = reviewRepository.save(review);

        updateRestaurantAverageScore(request.restaurantId());

        return reviewMapper.convertToReviewResponse(review);
    }

    @Override
    public ReviewResponse updateReview(ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(request.id())
                .orElseThrow(() -> new ItemNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));


        reviewMapper.updateReviewRequestToUser(review, request);
        reviewRepository.save(review);

        updateRestaurantAverageScore(review.getRestaurantId());

        return reviewMapper.convertToReviewResponse(review);
    }


    @Override
    @Transactional
    public void deleteReviewById(Long id) {
        Review deletedReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));

        String restaurantId = deletedReview.getRestaurantId();

        reviewRepository.deleteById(id);

        updateRestaurantAverageScore(restaurantId);
    }

    private void updateRestaurantAverageScore(String restaurantId) {

        List<Review> reviewList = reviewRepository.findReviewsByRestaurantId(restaurantId);
        int reviewCount = reviewRepository.countReviewByRestaurantId(restaurantId);

        double totalScore = 0;

        for (Review reviewElement : reviewList) {
            totalScore += reviewElement.getRate();
        }

        double averageScore;
        if (reviewCount > 0) {
            averageScore = totalScore / reviewCount;
        } else {
            averageScore = 0;
        }

        restaurantClient.updateRestaurantAverageScore(restaurantId,
                new RestaurantUpdateAverageScoreRequest(restaurantId, averageScore));
    }


}
