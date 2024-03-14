package com.egepancaroglu.restaurantservice.service.impl;

import com.egepancaroglu.restaurantservice.client.AddressFeignClient;
import com.egepancaroglu.restaurantservice.client.SolrClientService;
import com.egepancaroglu.restaurantservice.dto.AddressDTO;
import com.egepancaroglu.restaurantservice.dto.RestaurantDTO;
import com.egepancaroglu.restaurantservice.entity.Restaurant;
import com.egepancaroglu.restaurantservice.enums.Status;
import com.egepancaroglu.restaurantservice.exception.ItemNotFoundException;
import com.egepancaroglu.restaurantservice.mapper.RestaurantMapper;
import com.egepancaroglu.restaurantservice.repository.RestaurantRepository;
import com.egepancaroglu.restaurantservice.request.RestaurantSaveRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateAverageScoreRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateRequest;
import com.egepancaroglu.restaurantservice.response.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository mockRestaurantRepository;
    @Mock
    private RestaurantMapper mockRestaurantMapper;
    @Mock
    private SolrClientService mockSolrClientService;
    @Mock
    private AddressFeignClient mockAddressFeignClient;

    private RestaurantServiceImpl restaurantServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        restaurantServiceImplUnderTest = new RestaurantServiceImpl(mockRestaurantRepository, mockRestaurantMapper,
                mockSolrClientService, mockAddressFeignClient);
    }

    @Test
    void shouldGetRestaurantById() {
        RestaurantDTO expectedResult = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);

        Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(restaurant);

        RestaurantDTO restaurantDTO = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        RestaurantDTO result = restaurantServiceImplUnderTest.getRestaurantById("u23-b231a");

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetRestaurantById_RestaurantRepositoryReturnsAbsent() {
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantServiceImplUnderTest.getRestaurantById("u23-b231a"))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldGetAllRestaurants() {
        List<RestaurantDTO> expectedResult = List.of(new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0));

        Iterable<Restaurant> restaurants = List.of(new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE));
        when(mockRestaurantRepository.findAll()).thenReturn(restaurants);

        RestaurantDTO restaurantDTO = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        List<RestaurantDTO> result = restaurantServiceImplUnderTest.getAllRestaurants();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllRestaurants_RestaurantRepositoryReturnsNoItems() {
        when(mockRestaurantRepository.findAll()).thenReturn(Collections.emptyList());

        List<RestaurantDTO> result = restaurantServiceImplUnderTest.getAllRestaurants();

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldGetRecommendedRestaurantsByUserId() {
        List<RestaurantDTO> expectedResult = List.of(new RestaurantDTO("u23-b231a", "testRestaurant", "45.42,38.54", 1.0));

        ResponseEntity<RestResponse<List<AddressDTO>>> restResponseEntity = new ResponseEntity<>(
                new RestResponse<>(List.of(new AddressDTO(0L, "city", "state", "district", "street", "45.42,38.54", 0L)),
                        true), HttpStatus.OK);
        when(mockAddressFeignClient.getAddressesByUserId(0L)).thenReturn(restResponseEntity);

        List<Restaurant> restaurantList = List.of(new Restaurant("u23-b231a", "testRestaurant", "44.42,39.54", 1.0, Status.ACTIVE));
        when(mockSolrClientService.performSolrQuery("45.42,38.54")).thenReturn(restaurantList);

        List<RestaurantDTO> restaurantDTOList = List.of(new RestaurantDTO("u23-b231a", "testRestaurant", "45.42,38.54", 1.0));
        when(mockRestaurantMapper.convertRestaurantsToRestaurantDTOs(anyList()))
                .thenReturn(restaurantDTOList);

        List<RestaurantDTO> result = restaurantServiceImplUnderTest.getRecommendedRestaurantsByUserId(0L);

        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldSaveRestaurant() {
        RestaurantSaveRequest request = new RestaurantSaveRequest("testRestaurant", "45.42, 38.54");
        RestaurantDTO expectedResult = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);

        Restaurant restaurant = new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE);
        when(mockRestaurantMapper.convertSaveRequestToRestaurant(
                new RestaurantSaveRequest("testRestaurant", "45.42, 38.54"))).thenReturn(restaurant);

        Restaurant restaurant1 = new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE);
        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        RestaurantDTO restaurantDTO = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        RestaurantDTO result = restaurantServiceImplUnderTest.saveRestaurant(request);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldUpdateRestaurant() {
        RestaurantUpdateRequest request = new RestaurantUpdateRequest("u23-b231a", "testRestaurant", "45.42, 38.54");
        RestaurantDTO expectedResult = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);

        Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(restaurant);

        RestaurantDTO restaurantDTO = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        RestaurantDTO result = restaurantServiceImplUnderTest.updateRestaurant(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantMapper).updateRestaurantRequestToRestaurant(any(Restaurant.class),
                eq(new RestaurantUpdateRequest("u23-b231a", "testRestaurant", "45.42, 38.54")));
        verify(mockRestaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void shouldUpdateRestaurant_RestaurantRepositoryFindByIdReturnsAbsent() {
        RestaurantUpdateRequest request = new RestaurantUpdateRequest("u23-b231a", "testRestaurant", "45.42, 38.54");
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantServiceImplUnderTest.updateRestaurant(request))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldUpdateRestaurantAverageScore() {
        RestaurantUpdateAverageScoreRequest request = new RestaurantUpdateAverageScoreRequest("u23-b231a", 1.0);
        RestaurantDTO expectedResult = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);

        Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(restaurant);

        RestaurantDTO restaurantDTO = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        RestaurantDTO result = restaurantServiceImplUnderTest.updateRestaurantAverageScore(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void shouldUpdateRestaurantAverageScore_RestaurantRepositoryFindByIdReturnsAbsent() {
        RestaurantUpdateAverageScoreRequest request = new RestaurantUpdateAverageScoreRequest("u23-b231a", 1.0);
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantServiceImplUnderTest.updateRestaurantAverageScore(request))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldDeleteRestaurant() {
        restaurantServiceImplUnderTest.deleteRestaurant("u23-b231a");

        verify(mockRestaurantRepository).deleteById("u23-b231a");
    }

    @Test
    void shouldActivateRestaurant() {
        RestaurantDTO expectedResult = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);

        Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(restaurant);

        Restaurant restaurant1 = new Restaurant("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0, Status.ACTIVE);
        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        RestaurantDTO restaurantDTO = new RestaurantDTO("u23-b231a", "testRestaurant", "45.42, 38.54", 1.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        RestaurantDTO result = restaurantServiceImplUnderTest.activateRestaurant("u23-b231a");

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldActivateRestaurant_RestaurantRepositoryFindByIdReturnsAbsent() {
        when(mockRestaurantRepository.findById("u23-b231a")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantServiceImplUnderTest.activateRestaurant("u23-b231a"))
                .isInstanceOf(ItemNotFoundException.class);
    }
}
