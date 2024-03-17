package com.egepancaroglu.userreviewservice.request.restaurant;

/**
 * @author egepancaroglu
 */
public record RestaurantUpdateAverageScoreRequest(String restaurantId,
                                                  Double averageScore) {
}