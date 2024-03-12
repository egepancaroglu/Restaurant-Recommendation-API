package com.egepancaroglu.userreviewservice.request.review;

import jakarta.validation.constraints.*;

/**
 * @author egepancaroglu
 */

public record ReviewUpdateRequest(
        @NotNull(message = "Id can't be null or blank !")
        @Min(value = 0, message = "Id must be greater than or equal to 0")
        Long id,
        @NotBlank(message = "Comment can't be null or blank !")
        @Size(min = 3, max = 150)
        String comment,
        @Min(value = 1, message = "Rate must be least 1")
        @Max(value = 5, message = "Rate must be max 5")
        byte rate) {
}
