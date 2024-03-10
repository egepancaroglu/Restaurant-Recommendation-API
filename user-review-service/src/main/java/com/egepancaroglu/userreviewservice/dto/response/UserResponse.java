package com.egepancaroglu.userreviewservice.dto.response;

import java.time.LocalDate;

/**
 * @author egepancaroglu
 */
public record UserResponse(String userName,
                           String firstName,
                           String lastName,
                           String email,
                           LocalDate birthDate) {
}
