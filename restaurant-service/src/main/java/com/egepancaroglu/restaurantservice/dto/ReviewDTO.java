package com.egepancaroglu.restaurantservice.dto;

/**
 * @author egepancaroglu
 */
public record ReviewDTO(Long id,
                        String comment,
                        byte rate,
                        Long userId,
                        Long restaurantId) {
}

