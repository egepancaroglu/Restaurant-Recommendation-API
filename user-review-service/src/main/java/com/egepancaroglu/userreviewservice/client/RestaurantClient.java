package com.egepancaroglu.userreviewservice.client;

import com.egepancaroglu.userreviewservice.dto.response.ReviewResponse;
import com.egepancaroglu.userreviewservice.request.restaurant.RestaurantUpdateAverageScoreRequest;
import com.egepancaroglu.userreviewservice.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author egepancaroglu
 */

@FeignClient(name = "restaurant-service", url = "http://localhost:8081/api/v1/restaurants")
public interface RestaurantClient {
    @PutMapping("/{id}/averageScore")
    ResponseEntity<RestResponse<ReviewResponse>> updateRestaurantAverageScore(@PathVariable String id, @RequestBody RestaurantUpdateAverageScoreRequest request);

}
