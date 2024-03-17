package com.egepancaroglu.userreviewservice.request.review;

import jakarta.validation.constraints.*;


/**
 * @author egepancaroglu
 */

public record ReviewSaveRequest(
        @NotBlank(message = "Comment can't be null or blank !")
        @Size(min = 3, max = 150)
        String comment,
        @NotNull
        @Min(value = 1, message = "Rate must be least 1")
        @Max(value = 5, message = "Rate must be max 5")
        byte rate,
        @NotNull(message = "Id can't be null or blank !")
        String restaurantId,
        @NotNull(message = "Id can't be null or blank !")
        Long userId) {
}
