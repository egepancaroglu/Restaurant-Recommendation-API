package com.egepancaroglu.userreviewservice.dto;

import java.time.LocalDate;

/**
 * @author egepancaroglu
 */

public record UserDTO(Long id,
                      String userName,
                      String firstName,
                      String lastName,
                      String email,
                      LocalDate birthDate) {

}
