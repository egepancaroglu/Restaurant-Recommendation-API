package com.egepancaroglu.restaurantservice.service.impl;

import com.egepancaroglu.restaurantservice.dto.RestaurantDTO;
import com.egepancaroglu.restaurantservice.entity.Restaurant;
import com.egepancaroglu.restaurantservice.enums.Status;
import com.egepancaroglu.restaurantservice.exception.ItemNotFoundException;
import com.egepancaroglu.restaurantservice.general.ErrorMessages;
import com.egepancaroglu.restaurantservice.mapper.RestaurantMapper;
import com.egepancaroglu.restaurantservice.repository.RestaurantRepository;
import com.egepancaroglu.restaurantservice.request.RestaurantSaveRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateAverageScoreRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateRequest;
import com.egepancaroglu.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author egepancaroglu
 */

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantDTO getRestaurantById(String id) {

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.RESTAURANT_NOT_FOUND));

        return restaurantMapper.convertRestaurantToRestaurantDTO(restaurant);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {

        Iterable<Restaurant> restaurantList = restaurantRepository.findAll();
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();

        for (Restaurant restaurant : restaurantList) {
            RestaurantDTO restaurantDTO = restaurantMapper.convertRestaurantToRestaurantDTO(restaurant);
            restaurantDTOList.add(restaurantDTO);
        }

        return restaurantDTOList;

    }

    @Override
    public RestaurantDTO saveRestaurant(RestaurantSaveRequest request) {

        Restaurant restaurant = restaurantMapper.convertSaveRequestToRestaurant(request);

        restaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.convertRestaurantToRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantUpdateRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.id()).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.RESTAURANT_NOT_FOUND));
        restaurantMapper.updateRestaurantRequestToRestaurant(restaurant, request);

        restaurantRepository.save(restaurant);

        return restaurantMapper.convertRestaurantToRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO updateRestaurantAverageScore(RestaurantUpdateAverageScoreRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId()).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.RESTAURANT_NOT_FOUND));

        restaurant.setAverageScore(request.averageScore());

        restaurantRepository.save(restaurant);

        return restaurantMapper.convertRestaurantToRestaurantDTO(restaurant);

    }

    @Override
    public void deleteRestaurant(String id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public RestaurantDTO activateRestaurant(String id) {

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.RESTAURANT_NOT_FOUND));

        restaurant.setStatus(Status.ACTIVE);
        restaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.convertRestaurantToRestaurantDTO(restaurant);

    }


}
