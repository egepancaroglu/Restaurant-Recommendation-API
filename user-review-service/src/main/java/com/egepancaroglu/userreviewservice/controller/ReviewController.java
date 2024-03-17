package com.egepancaroglu.userreviewservice.controller;

import com.egepancaroglu.userreviewservice.dto.ReviewDTO;
import com.egepancaroglu.userreviewservice.dto.response.ReviewResponse;
import com.egepancaroglu.userreviewservice.request.review.ReviewSaveRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewUpdateRequest;
import com.egepancaroglu.userreviewservice.response.RestResponse;
import com.egepancaroglu.userreviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author egepancaroglu
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<RestResponse<List<ReviewDTO>>> getAllReviews() {
        return ResponseEntity.ok(RestResponse.of(reviewService.getAllReviews()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<ReviewDTO>> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.of(reviewService.getReviewById(id)));
    }

    @GetMapping("/with-restaurantId/{restaurantId}")
    public ResponseEntity<RestResponse<List<ReviewDTO>>> getReviewsByRestaurantId(@PathVariable String restaurantId) {
        return ResponseEntity.ok(RestResponse.of(reviewService.getReviewsByRestaurantId(restaurantId)));
    }

    @PostMapping
    public ResponseEntity<RestResponse<ReviewResponse>> createReview(@Valid @RequestBody ReviewSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(reviewService.saveReview(request)));
    }


    @PutMapping
    public ResponseEntity<RestResponse<ReviewResponse>> updateReview(@Valid @RequestBody ReviewUpdateRequest request) {
        return ResponseEntity.ok(RestResponse.of(reviewService.updateReview(request)));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReviewById(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }

}
