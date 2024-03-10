package com.egepancaroglu.restaurantservice.request;



import javax.validation.constraints.*;

/**
 * @author egepancaroglu
 */


public record RestaurantSaveRequest(
        @NotBlank(message = "Name can't be null or blank !")
        @Size(min = 2, max = 50)
        String name,
        @NotNull
        String location) {

}
