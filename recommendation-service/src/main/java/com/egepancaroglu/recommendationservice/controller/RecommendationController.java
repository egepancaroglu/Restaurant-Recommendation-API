package com.egepancaroglu.recommendationservice.controller;

import com.egepancaroglu.recommendationservice.dto.RestaurantDTO;
import com.egepancaroglu.recommendationservice.response.RestResponse;
import com.egepancaroglu.recommendationservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author egepancaroglu
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommends")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommendRestaurants/{userId}")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getRecommendedRestaurantsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(RestResponse.of(recommendationService.getRecommendedRestaurantsByUserId(userId)));
    }

}
