package com.egepancaroglu.restaurantservice.request;


import javax.validation.constraints.*;

/**
 * @author egepancaroglu
 */
public record RestaurantUpdateRequest(
        @NotNull(message = "Id can't be null")
        String id,
        @NotBlank(message = "Name can't be null or blank !")
        @Size(min = 2, max = 50)
        String name,
        @NotNull
        @DecimalMax(value = "90.000000")
        @DecimalMin(value = "-90.000000")
        Double latitude,
        @NotNull
        @DecimalMax(value = "90.000000")
        @DecimalMin(value = "-90.000000")
        Double longitude) {
}
