package com.egepancaroglu.recommendationservice.dto;

/**
 * @author egepancaroglu
 */
public record RestaurantResponse(String id,
                                 String name,
                                 String location,
                                 Double averageScore) {
}