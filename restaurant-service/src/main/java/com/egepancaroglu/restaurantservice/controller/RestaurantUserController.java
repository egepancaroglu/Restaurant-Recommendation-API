package com.egepancaroglu.restaurantservice.controller;

import com.egepancaroglu.restaurantservice.client.AddressFeignClient;
import com.egepancaroglu.restaurantservice.dto.AddressDTO;
import com.egepancaroglu.restaurantservice.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author egepancaroglu
 */

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class RestaurantUserController {

    private final AddressFeignClient addressFeignClient;

    @GetMapping("/with-userId/{userId}")
    public ResponseEntity<RestResponse<List<AddressDTO>>> getAddressesByUserId(@PathVariable Long userId) {

        return addressFeignClient.getAddressesByUserId(userId);

    }

}
