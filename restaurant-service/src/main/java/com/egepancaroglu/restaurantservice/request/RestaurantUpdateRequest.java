package com.egepancaroglu.restaurantservice.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author egepancaroglu
 */
public record RestaurantUpdateRequest(
        @NotNull(message = "Id can't be null")
        String id,
        @NotBlank(message = "Name can't be null or blank !")
        @Size(min = 2, max = 50)
        String name,
        @NotBlank(message = "Location can't be null or blank !")
        String location) {
}
