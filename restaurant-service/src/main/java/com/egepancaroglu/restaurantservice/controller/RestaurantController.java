package com.egepancaroglu.restaurantservice.controller;

import com.egepancaroglu.restaurantservice.dto.RestaurantDTO;
import com.egepancaroglu.restaurantservice.request.RestaurantSaveRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateRequest;
import com.egepancaroglu.restaurantservice.response.RestResponse;
import com.egepancaroglu.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author egepancaroglu
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<RestaurantDTO>> getRestaurantById(@PathVariable String id) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.getRestaurantById(id)));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getAllRestaurants() {
        return ResponseEntity.ok(RestResponse.of(restaurantService.getAllRestaurants()));
    }

    @GetMapping("/recommend/{userId}")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getRecommendedRestaurantsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.getRecommendedRestaurantsByUserId(userId)));
    }

    @PostMapping
    public ResponseEntity<RestResponse<RestaurantDTO>> createRestaurant(@Valid @RequestBody RestaurantSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(restaurantService.saveRestaurant(request)));
    }

    @PutMapping
    public ResponseEntity<RestaurantDTO> updateRestaurant(@Valid @RequestBody RestaurantUpdateRequest request) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(request));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<RestResponse<RestaurantDTO>> activateRestaurant(@PathVariable String id) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.activateRestaurant(id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
    }


}
