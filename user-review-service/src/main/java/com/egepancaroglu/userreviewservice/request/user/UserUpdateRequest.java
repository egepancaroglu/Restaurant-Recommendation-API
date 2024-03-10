package com.egepancaroglu.userreviewservice.request.user;

import com.egepancaroglu.userreviewservice.entity.enums.Status;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * @author egepancaroglu
 */

public record UserUpdateRequest(
        @NotNull(message = "Id can't be null or blank !")
        Long id,
        @NotBlank(message = "User Name can't be null or blank !")
        @Size(min = 2, max = 100)
        String userName,
        @NotBlank(message = "First Name can't be null or blank !")
        @Size(min = 2, max = 100)
        String firstName,
        @NotBlank(message = "Last Name can't be null or blank !")
        @Size(min = 2, max = 100)
        String lastName,
        @NotNull
        @Email
        String email,
        @NotNull
        Status status,
        @Past
        LocalDate birthDate) {
}
