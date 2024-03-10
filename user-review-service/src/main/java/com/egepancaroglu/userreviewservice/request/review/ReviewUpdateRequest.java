package com.egepancaroglu.userreviewservice.request.review;

import com.egepancaroglu.userreviewservice.entity.enums.Rate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author egepancaroglu
 */

public record ReviewUpdateRequest(
        @NotNull(message = "Id can't be null or blank !")
        Long id,
        @NotBlank(message = "Comment can't be null or blank !")
        @Size(min = 3, max = 150)
        String comment,
        @NotBlank(message = "Rate can't be null or blank !")
        Rate rate) {
}
