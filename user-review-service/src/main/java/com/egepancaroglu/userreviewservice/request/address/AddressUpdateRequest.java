package com.egepancaroglu.userreviewservice.request.address;

import jakarta.validation.constraints.*;

/**
 * @author egepancaroglu
 */
public record AddressUpdateRequest(
        @NotNull(message = "Id can't be null or blank !")
        Long id,
        @NotBlank(message = "City can't be null or blank !")
        @Size(min = 2, max = 50)
        String city,
        @NotBlank(message = "State can't be null or blank !")
        @Size(min = 2, max = 50)
        String state,
        @NotBlank(message = "District can't be null or blank !")
        @Size(min = 2, max = 50)
        String district,
        @NotBlank(message = "Street can't be null or blank !")
        @Size(min = 2, max = 50)
        String street,
        @NotNull
        @DecimalMax(value = "90.000000")
        @DecimalMin(value = "-90.000000")
        Double latitude,
        @NotNull
        @DecimalMax(value = "90.000000")
        @DecimalMin(value = "-90.000000")
        Double longitude) {
}
