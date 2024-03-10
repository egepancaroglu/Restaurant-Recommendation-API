package com.egepancaroglu.userreviewservice.service.impl;

import com.egepancaroglu.userreviewservice.dto.ReviewDTO;
import com.egepancaroglu.userreviewservice.entity.Review;
import com.egepancaroglu.userreviewservice.entity.enums.Rate;
import com.egepancaroglu.userreviewservice.exception.ItemNotFoundException;
import com.egepancaroglu.userreviewservice.general.ErrorMessages;
import com.egepancaroglu.userreviewservice.mapper.ReviewMapper;
import com.egepancaroglu.userreviewservice.repository.ReviewRepository;
import com.egepancaroglu.userreviewservice.request.review.ReviewSaveRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewUpdateRequest;
import com.egepancaroglu.userreviewservice.service.ReviewService;
import com.egepancaroglu.userreviewservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public ReviewDTO saveReview(ReviewSaveRequest request) {

        Review review = reviewMapper.convertToReview(request);

        review.setUser(userService.getUserEntity(request.userId()));

        review = reviewRepository.save(review);

        return reviewMapper.convertToReviewDTO(review);

    }



    @Override
    public ReviewDTO updateReview(ReviewUpdateRequest request) {

        Review review = reviewRepository.findById(request.id()).orElseThrow();
        reviewMapper.updateReviewRequestToUser(review, request);

        reviewRepository.save(review);

        return reviewMapper.convertToReviewDTO(review);

    }


    @Override
    public void deleteReviewById(Long id) {

        reviewRepository.deleteById(id);

    }
}
