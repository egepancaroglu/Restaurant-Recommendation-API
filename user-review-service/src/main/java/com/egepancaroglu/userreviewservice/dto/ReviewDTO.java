package com.egepancaroglu.userreviewservice.dto;

import com.egepancaroglu.userreviewservice.entity.enums.Rate;

/**
 * @author egepancaroglu
 */

public record ReviewDTO(Long id,
                        String comment,
                        Rate rate,
                        Long userId) {
}
