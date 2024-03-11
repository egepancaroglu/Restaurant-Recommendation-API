package com.egepancaroglu.userreviewservice.dto;

/**
 * @author egepancaroglu
 */

public record AddressDTO(Long id,
                         String city,
                         String state,
                         String district,
                         String street,
                         String location,
                         Long userId) {
}
