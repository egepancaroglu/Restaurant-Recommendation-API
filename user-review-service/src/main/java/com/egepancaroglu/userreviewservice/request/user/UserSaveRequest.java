package com.egepancaroglu.userreviewservice.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * @author egepancaroglu
 */

public record UserSaveRequest(
        @NotBlank(message = "User Name can't be null or blank !")
        @Size(min = 2, max = 100)
        String userName,
        @Size(min = 2, max = 100)
        @NotBlank(message = "First Name can't be null or blank !")
        @Size(min = 2, max = 100)
        String firstName,
        @NotBlank(message = "Last Name can't be null or blank !")
        @Size(min = 2, max = 100)
        String lastName,
        @Email
        String email,
        @Past
        LocalDate birthDate) {

}




