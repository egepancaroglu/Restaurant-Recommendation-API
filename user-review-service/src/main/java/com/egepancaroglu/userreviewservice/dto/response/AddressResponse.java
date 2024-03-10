package com.egepancaroglu.userreviewservice.dto.response;

/**
 * @author egepancaroglu
 */
public record AddressResponse(String city,
                              String state,
                              String district,
                              String street,
                              Long userId){
}
