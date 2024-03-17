package com.egepancaroglu.userreviewservice.dto;

/**
 * @author egepancaroglu
 */
public record ReviewResponse(
        String comment,
        byte rate,
        Long userId,
        String restaurantId) {
}
