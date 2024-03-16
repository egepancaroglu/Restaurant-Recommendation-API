package com.egepancaroglu.recommendationservice.service;

import com.egepancaroglu.recommendationservice.dto.RestaurantDTO;

import java.util.List;

/**
 * @author egepancaroglu
 */


public interface RecommendationService {

    List<RestaurantDTO> getRecommendedRestaurantsByUserId(Long userId);


}
