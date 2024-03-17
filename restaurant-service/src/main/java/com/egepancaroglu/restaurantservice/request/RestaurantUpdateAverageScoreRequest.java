package com.egepancaroglu.restaurantservice.request;

/**
 * @author egepancaroglu
 */
public record RestaurantUpdateAverageScoreRequest(String restaurantId,
                                                  Double averageScore) {
}
